import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import classification_report
from helper_function import validate_battery_actions


def clip_actions_to_battery(
    actions,
    capacity=10.0,
    initial_soc=5.0,
    timestep_hours=0.25,
    reset_daily=True,
):
    soc = initial_soc
    fixed = []

    intervals_per_day = int(round(24 / timestep_hours))  # 96 pentru 15 minute

    for i, a in enumerate(actions):
        # reset zilnic al SoC (conform enuntului)
        if reset_daily and i > 0 and i % intervals_per_day == 0:
            soc = initial_soc

        if a > 0:
            # incarcare: nu putem depasi capacitatea
            max_charge = capacity - soc
            adj = min(a, max_charge)
        elif a < 0:
            # descarcare: nu putem cobori sub 0
            max_discharge = soc
            adj = max(a, -max_discharge)
        else:
            adj = 0.0

        # rotunjim la multipli de 0.1 MWh (cerinta problemei)
        adj = round(adj, 1)

        soc += adj
        fixed.append(adj)

    return fixed


df = pd.read_csv("Dataset.csv")

start_str = df["Time interval (CET/CEST)"].str.split(" - ").str[0]
df["start"] = pd.to_datetime(start_str, format="%d.%m.%Y %H:%M")

df["hour"] = df["start"].dt.hour
df["quarter"] = df["start"].dt.minute // 15
df["dayofweek"] = df["start"].dt.dayofweek   # 0=luni
df["month"] = df["start"].dt.month
df["date"] = df["start"].dt.date

LOW_Q = 0.50   # sub quantil -> buy (pret destul de mic)
HIGH_Q = 0.50  # peste quantil -> sell (pret destul de mare)
# Daca vrei buy/sell doar la extreme, poti pune de ex. 0.25 si 0.75.

group = df.groupby("date")["Price"]
low_thr = group.transform(lambda x: x.quantile(LOW_Q))
high_thr = group.transform(lambda x: x.quantile(HIGH_Q))

df["label"] = "hold"
df.loc[df["Price"] <= low_thr, "label"] = "buy"
df.loc[df["Price"] >= high_thr, "label"] = "sell"

print("Distributia label-urilor (proportii):")
print(df["label"].value_counts(normalize=True))

n = len(df)
train_end = int(0.7 * n)
val_end = int(0.85 * n)

train = df.iloc[:train_end]
val   = df.iloc[train_end:val_end]
test  = df.iloc[val_end:]

features = ["hour", "quarter", "dayofweek", "month"]

X_train = train[features]
y_train = train["label"]

X_val = val[features]
y_val = val["label"]

X_test = test[features]
y_test = test["label"]

model = RandomForestClassifier(
    n_estimators=100,      # numarul de arbori
    max_depth=15,         # limitam adancimea ca sa reducem overfitting-ul
    min_samples_leaf=5,   # fiecare frunza are cel putin 5 exemple
    max_features="sqrt",  # feature bagging clasic
    random_state=0,
    n_jobs=-1,
)
model.fit(X_train, y_train)

print("\nPerformanta pe validation (clasificare pura):")
y_val_pred = model.predict(X_val)
print(classification_report(y_val, y_val_pred))

print("Performanta pe test (doar pentru tine):")
y_test_pred = model.predict(X_test)
print(classification_report(y_test, y_test_pred))


def proba_to_action(probas, classes):
    classes_list = classes.tolist()

    def get_p(lbl):
        return probas[classes_list.index(lbl)] if lbl in classes_list else 0.0

    p_buy = get_p("buy")
    p_sell = get_p("sell")
    p_hold = get_p("hold")

    # praguri
    STRONG = 0.7
    WEAK = 0.5

    # daca modelul e mai sigur pe buy decat pe sell/hold
    if p_buy >= max(p_sell, p_hold):
        if p_buy >= STRONG:
            return 1.0
        elif p_buy >= WEAK:
            return 0.7
        else:
            return 0.1

    # daca modelul e mai sigur pe sell decat pe buy/hold
    if p_sell >= max(p_buy, p_hold):
        if p_sell >= STRONG:
            return -1.0
        elif p_sell >= WEAK:
            return -0.8
        else:
            return -0.1

    # altfel, fallback = hold
    return 0.0


sample = pd.read_csv("sample_submission.csv")

start_str_sub = sample["Time interval (CET/CEST)"].str.split(" - ").str[0]
sample["start"] = pd.to_datetime(start_str_sub, format="%d.%m.%Y %H:%M")

sample["hour"] = sample["start"].dt.hour
sample["quarter"] = sample["start"].dt.minute // 15
sample["dayofweek"] = sample["start"].dt.dayofweek
sample["month"] = sample["start"].dt.month

X_sub = sample[features]

# folosim predict_proba in loc de predict
probas_sub = model.predict_proba(X_sub)
classes = model.classes_

raw_actions = [proba_to_action(p, classes) for p in probas_sub]

fixed_actions = clip_actions_to_battery(
    raw_actions,
    capacity=10.0,
    initial_soc=5.0,
    timestep_hours=0.25,
    reset_daily=True,
)

# validare cu helper-ul dat
is_valid, warnings = validate_battery_actions(fixed_actions)
print("\nValidare actiuni pe battery:")
print("Valid:", is_valid)
print("Numar de warnings:", len(warnings))

sample["Position"] = fixed_actions
sample[["Time interval (CET/CEST)", "Position"]].to_csv(
    "incercare.csv",
    index=False,
)
