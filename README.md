# This repository contains TradeStore service
All the given validations are performed and unit testing is performed.

Scope of Improvement:
```
1. Loading the data via JPA entities and performing cred operations.
2. Currently scheduler isconfigured to run everyday at 12:00 A.M. for all the data
with db integration we can run the scheduler on delta.
3. Instead of loading data from test method we can wirte one end point with postMaping which body has @RequestBoby in controller class where user can uplpoad the trade data in excel or csv format or send data in jason format.
4. We can write the one end poin with getMapping in controller class which will retun all the active tredaes.
5. We can write one Update end point with PutMapiing as well where user can update the trades accaording to their needs.
```
