Batcher Library for Android
====

_Batcher_ is a new library aimed at descreasing your apps' impact on the battery life of a device. [Battery life](http://developer.android.com/training/monitoring-device-state/index.html) is a big deal when it comes to users, and they may  remove your app if they think it drains too much battery. 

How does this library help?
----

Batcher helps by giving you a simple framework with which to batch expensive operations (e.g. file uploads) depending on factors such as [battery life](https://github.com/Espiandev/batcher/blob/master/library/src/com/espian/batcher/HighBatteryCondition.java), [connection type](https://github.com/Espiandev/batcher/blob/master/library/src/com/espian/batcher/BasicWifiCondition.java) & [simple timings](https://github.com/Espiandev/batcher/blob/master/library/src/com/espian/batcher/TimeCondition.java). 
