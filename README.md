Batcher Library for Android
====

_Batcher_ is a new library aimed at descreasing your apps' impact on the battery life of a device. [Battery life](http://developer.android.com/training/monitoring-device-state/index.html) is a big deal when it comes to users, and they may  remove your app if they think it drains too much battery. 

How does this library help?
----

Batcher helps by giving you a simple framework with which to batch expensive operations (e.g. file uploads) depending on factors such as [battery life](https://github.com/Espiandev/batcher/blob/master/library/src/com/espian/batcher/HighBatteryCondition.java), [connection type](https://github.com/Espiandev/batcher/blob/master/library/src/com/espian/batcher/BasicWifiCondition.java) & [simple timings](https://github.com/Espiandev/batcher/blob/master/library/src/com/espian/batcher/TimeCondition.java). 

The library works in a very simple way. The batch operation is triggered when any conditions you have set have been met. Batcher has been designed to be extended easily - the conditions can be anything you like! See Usage for examples in how to create your own conditions. 

Usage
----

1. Extend Batcher, providing the type of input you wish to perform the batch operation on.
2. Override performOperation(List<Inputs>) to specify the operation you wish to batch.
3. Add either a single ShotCondition, or a ShotCondition and a SwitchCondition, and away you go!

There are two base classes, ShotCondition and SwitchCondition, that can be extended to suit any conditions you want your batching to meet. A third, CacheListenerShotCondition, is available for conditions which require manipulation or reading of the cached input values. ShotCondition is suitable for overriding when your condition is infrequent or can be triggered by an enviromental change, such as going over a cache size limit or every X second. In contrast, SwitchConditions are used in more of a secondary role, to prevent a batch being triggered under certain conditions (e.g. Airplane mode).
