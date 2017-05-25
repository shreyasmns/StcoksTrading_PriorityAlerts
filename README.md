# StcoksTrading_PriorityAlerts
New alerts feature called “Stocks Your Friends are Trading”. The alerts logic is based on the following naïve rules: • A user should only be alerted to stocks their friends have bought or sold in the past week.

Suppose your team needs to launch a new alerts feature called “Stocks Your
Friends are Trading”. The alerts logic is based on the following naïve rules:

• A user should only be alerted to stocks their friends have bought or sold in
the past week.

• BUY and SELL alerts are driven by the net number of friends buying a
stock – if 5 friends bought shares in GOOG, and 2 friends sold shares in
GOOG, the net number of friends buying GOOG is 3. If the net number is
> 0, we'd indicate more friends are buying GOOG (BUY alert); if < 0, more
friends are selling GOOG (SELL alert); and if 0, we ignore.

• Alerts are prioritized by activity trend, e.g. a net number of 5 friends selling
GOOG is shown before a net number of 4 friends buying YHOO.
