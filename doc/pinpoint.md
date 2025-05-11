## pinponit 结构
Pinpoint是一个开源的APM（应用性能管理）系统，主要用于监控和管理Java应用程序的性能。它提供了实时的性能指标、分布式追踪和诊断等功能，帮助开发和运维快速定位和解决应用程序中的性能问题。

pinpoint其他部分不变，Hbase由2.3.3升级到2.5.5后经常罢工，暂时没排查到原因，我们写个脚本来监控pinpoint服务，一旦pinpoint哪个服务罢工，就自动去重启它

pinpoint由四部分组成，分别是pinpoint agent、pinpoint collector、pinpoint web、HBase

Pinpoint Agent：无侵入式收集应用端监控数据，，只需要在启动命令中加入部分参数即可（探针）;

Pinpoint Collector：数据收集，将Agent发送过来的数据接收并存储到HBase;

Pinpoint Web：pinpint前端展示页面，可视化展示监控详情;

HBase：数据库，用于保存pinpoint监控的数据；

Pinpoint Agent随着服务运行，我们需要确保的是Pinpoint Collector，Pinpoint Web，HBase这三部分的运行，保证Pinpoint Agent传回来的数据能保存到数据库，并通过前端页面进行展示

## 部署
