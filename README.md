600.250 User Interfaces & Mobile Applications
Team Project Part V
Group 5: Mariya Kazachkova, Kiki Chang, Ye Chan Kim, Joo Sung Kim

Grocery Helper App:
Before opening the app it required to give permission to access storage and gallery
or else the app will not work.

Your List Feature:
We used the inbuilt Android Studio SQLite database to store the grocery items.
To implement a number picker for the quantity and expiration date reminder 
section we used the ScrollableNumberPicker library.

Receipt Feature:
We also used the a second SQLite database to store the receipts of the user.

Statistics Feature:
To make the calendar section of this feature we used the CaldroidFragment library.
Also to make the animated graphs we imported github.mikephil.charting

Expiration Reminder:
To make a push notification appear for a delayed amount of time, the Notification.builder
libary was used to make a custom notification and the AlarmManager Libary was used
to set the push notification to go off at 8:00 A.M.
When an item is added to this feature the number of expiration days will automatically
be set for the same item when it is added in the list fragment. For example, if the user
sets and expiration reminder for Apples as 2 days, next time the user adds Apples in the
list feature, the expiration reminder will be automatically set to 2 days.

