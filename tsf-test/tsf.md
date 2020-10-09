
TSF技术培训
上机指导书
V2.1








深圳市腾讯计算机系统有限公司


目    录
实验 1 :开发第一个Spring Cloud应用程序	5
1.1  实验环境	5
1.2  实验介绍	5
1.3  实验步骤	6
1.4  实验结果检验	17
1.5  实验结果	18
实验 2 ：使用TSF虚拟机部署微服务	19
2.1  实验环境	19
2.2  实验介绍	19
2.3  实验步骤	20
2.4  实验结果检验	36
2.5  实验结果	38
实验 3 ：使用TSF容器部署微服务	39
3.1  实验环境	39
3.2  实验介绍	39
3.3  Docker基础实验	39
3.4  使用TSF容器部署微服务	43
实验 4 ：TSF运维管理	60
4.1  实验环境	60
4.2  TSF日志服务	60
4.3  TSF调用链查询	68
4.4  TSF弹性伸缩	72
实验 5 ：使用TSF服务治理	78
5.1  实验环境	78
5.2  实验介绍	78
5.3  服务治理开发准备	79
5.4  服务基本操作	81
5.5  使用服务鉴权功能	84
5.6  使用服务限流功能	90
5.7  使用服务路由功能	97
实验 6 ：使用TSF配置管理功能	105
6.1  实验环境	105
6.2  实验介绍	105
6.3  配置管理开发准备	106
6.4  实验步骤	110
6.5  实验结果检验	115
6.6  实验结果	116
实验 7 ：TSF与API网关使用	117
7.1  实验环境	117
7.2  实验介绍	117
7.3  实验步骤	118
7.4  实验结果检验	124
7.5  实验结果	125
实验 8 ：使用TSF分布式事务功能	126
8.1  实验环境	126
8.2  实验介绍	126
8.3 实验步骤	127
8.4  实验结果检验	137
8.5  实验结果	139



实验 1:开发第一个Spring Cloud应用程序
1.1  实验环境
1.Java软件开发工具包JDK安装
(1)版本：1.8
2.Maven安装：
(1)版本：3.5及以上
(2)修改setting配置为国内下载镜像
(3)Maven编码过程中需要下载依赖，需要网络环境
3.Eclipse或者IDEA，此实验采用Eclipse作为开发工具

1.2  实验介绍
Spring Cloud是基于Spring Boot 提供的一套微服务解决方案，Spring Cloud利用Spring Boot的开发便利性，巧妙的简化了分布式系统基础设施的开发，为开发人员提供了快速构建分布式系统的工具。Spring Cloud的核心是服务注册与发现，后面在使用整套Spring Cloud的功能时，都需要先把开发好的服务注册到服务注册中心。
本实验将带领学员完整地开发一个基于Spring Cloud的微服务应用程序，旨在帮助学员快速掌握Spring Cloud微服务架构的核心知识。




Spring Cloud微服务的架构

1.3  实验步骤
【Step 1】开发Eureka Server服务注册中心
1)创建新的Maven工程：eureka-server。



2)项目创建完成后结构如下：

3)在上一步创建的Eureka-server项目根目录下的pom.xml依赖配置文件（project节点下）中修改为下列的依赖。














4)创建EurekaServerApplication.java主启动类

EurekaServerApplication.java启动类代码如下


















5)在项目src/main/resouces资源文件夹中添加application.yml配置文件

在application.yml中添加配置内容

6)启动EurekaServerApplication主类（类文件上右键 -> run as -> Java Application），在浏览器中访问：http://localhost:8000/；如下界面就是Eureka的web界面，当前没有注册任何服务。


【Step 2】开发Eureka-Provider服务提供者
1)在eclipse中通过maven方式创建eureka-provide项目（具体步骤参照Step1中所示）；项目创建完成后如下图：

2)在项目根目录下的pom.xml配置文件中的project节点下加入以下依赖
















3)
创建启动主程序：EurekaProviderApplication.java

主启动类EurekaProviderApplication代码如下








4)新增控制器类：HelloController.java

控制器类HelloController.java代码如下：

5)在src/main/resources资源文件夹中新增配置文件application.yml


application.yml配置文件内容如下：
6)运行EurekaProviderApplication.java启动类，eureka-provider项目会自动注册到step1中开发的eureka-server注册中心；在浏览器中再次访问：http://localhost:8000/







【Step 3】开发Eureka- Consumer服务消费者
1)在eclipse中通过maven方式创建eureka-consumer项目（具体步骤参照Step1中所示）；项目创建完成后如下图：





2)在项目根目录下的pom.xml配置文件中的project节点下加入以下依赖















3)创建启动主程序：EurekaConsumerApplication.java

主启动类EurekaConsumerApplication代码如下

4)新增控制器类：HelloConsumerController.java


控制器类HelloConsumerController.java代码如下：

5)在src/main/resources资源文件夹中新增配置文件application.yml

application.yml配置文件内容如下：





6)运行EurekaConsumerApplication.java启动类，eureka-consumer项目会自动注册到step1中开发的eureka-server注册中心；在浏览器中再次访问：http://localhost:8000/

7)在浏览器中访问 http://localhost:8002/consumer/hello/test



1.4  实验结果检验
1)访问：“http://localhost:8000/”链接能看到注册到eureka-server的2个服务（eureka-provider、eureka-consumer）



2)访问：“http://localhost:8002/consumer/hello/要传递的参数”；能根据输入的参数返回对应的内容





1.5  实验结果
是否已完成本节实验，请将实验结果记录如下：    □ 是      □ 否
                                                                               
                                                                               


实验 2：使用TSF虚拟机部署微服务
2.1  实验环境
1.Java软件开发工具包JDK安装
(1)版本：1.8
2.Maven安装：
(1)版本：3.5及以上
(2)修改setting配置为国内下载镜像
(3)Maven编码过程中需要下载依赖，需要网络环境
3.Eclipse或者IDEA，此实验采用Eclipse作为开发工具
4.Maven项目的创建步骤参考“实验 1”
5.能够访问腾讯云官网https://cloud.tencent.com
6.要求有腾讯云账号，并且已经开通腾讯微服务平台TSF权限
7.实验过程中需要购买云虚拟机
8.确保已完成了TSF的 SDK 下载；详情参考：https://cloud.tencent.com/document/product/649/20231
2.2  实验介绍
腾讯微服务平台 （Tencent Service Framework） 是一个围绕着应用和微服务的 PaaS 平台，提供应用全生命周期管理、数据化运营、立体化监控和服务治理等功能。TSF 拥抱 Spring Cloud 、Service Mesh 微服务框架，帮助企业客户解决传统集中式架构转型的困难，打造大规模高可用的分布式系统架构，实现业务、产品的快速落地。
在上一个实验中我们已经学会开发一个简单的Spring Cloud应用，本实验将带领学员从Spring Cloud应用迁入到腾讯微服务平台TSF的开发，并且带领学员在TSF中使用虚拟机方式部署微服务应用；帮助学员快速掌握使用TSF虚拟机部署微服务。

2.3  实验步骤
【Step 1】开发provider-demo服务提供者项目
1)创建新的Maven工程：provider-demo；项目创建完成后如下图：

2)在项目根目录下的pom.xml添加依赖配置,pom.xml代码如下：




























3)在src/main/java创建启动类ProviderApplication.java，开启服务注册发现

ProviderApplication.java代码如下：



4)创建Controller，提供echo服务：

EchoController.java 代码如下：










5)创建配置文件application.yml：

application.yml配置代码如下：

6)在eclipse中项目上右键-》run as -》maven install，或者在工程的主目录下，使用 Maven 命令 mvn clean package 进行打包，执行成功后项目target目录下会生成打包后的jar包：


【Step 2】开发consumer-demo服务消费者项目
1)创建新的Maven工程：consumer-demo；项目创建完成后如下图：

2)在项目根目录下的pom.xml添加依赖配置,pom.xml代码如下：





















3)在src/main/java创建启动类ConsumerApplication.java，
与服务提供者 provider-demo 相比，除了开启服务与注册外，还需要添加两项配置才能使用 RestTemplate、AsyncRestTemplate、FeignClient 这三个客户端；
添加 @LoadBalanced 注解将 RestTemplate 与 AsyncRestTemplate 与服务发现结合。
使用 @EnableFeignClients 注解激活 FeignClients

ConsumerApplication.java代码如下：














4)在使用 EchoService 的 FeignClient 之前，还需要完善它的配置。配置服务名以及方法对应的 HTTP 请求，服务名 为 provider-demo 工程中配置的服务名 provider-demo：

EchoService .java代码如下






5)创建一个 Controller 供调用测试
/echo-rest/* 验证通过 RestTemplate 去调用服务提供者。
/echo-async-rest/* 验证通过 AsyncRestTemplate 去调用服务提供者。
/echo-feign/* 验证通过 FeignClient 去调用服务提供者。

Controller.java 代码如下：















6)创建配置文件application.yml：

application.yml配置代码如下：

7)在eclipse中项目上右键-》run as -》maven install，或者在工程的主目录下，使用 Maven 命令 mvn clean package 进行打包，。执行成功后项目target目录下会生成打包后的jar包：


【Step 3】在TSF虚拟机中部署微服务项目
1)创建虚拟机集群
登录 TSF 控制台。
在 左侧导航栏 中，单击 集群，进入集群列表页。
在集群列表页，单击【新建集群】。

设置集群的基本信息。
集群名称：集群名称，不超过 60 个字符。
集群类型：选择集群类型。
虚拟机集群：虚拟机集群中的云服务器用于部署虚拟机应用。
容器集群：容器集群中的云服务器用于部署容器应用。
集群网络：为集群内主机分配在云服务器网络地址范围内的 IP 地址。参阅 集群及容器网络设置。
容器网络（仅适用于容器集群）：为集群内容器分配在容器网络地址范围内的 IP 地址。集群及容器网络设置
集群描述：集群的描述，不超过 200 个字符。


2)将云服务器导入集群
在导入主机之前请先确定已经购买了腾讯云上的主机使用服务
在 集群列表页 中，单击目标集群ID/集群名，进入集群详情页 。
单击云服务器列表上方的 【导入云主机】 按钮。
从集群所在 VPC 的云服务器列表中，选择需要添加到集群的主机。


云服务器配置
登录方式：提供三种对应登录方式。
设置密码：请根据提示设置对应密码。
立即关联密钥：密钥对是通过一种算法生成的一对参数，是一种比常规密码更安全的登录云服务器的方式。详细参阅 SSH 密钥。
自动生成密码：自动生成的密码将通过站内信发送给您。
导入完成后所在集群的云主机列表会显示导入的虚拟机，如下：




3)TSF中创建虚拟机应用provi-demo和consumer-demo
在 左侧导航栏，单击 应用管理，进入应用列表。
在应用列表上方单击【新建应用】。

设置应用信息后，单击【提交】按钮。
应用类型：虚拟机应用将部署在云服务器上
部署方式：选择虚拟机部署
创建应用完成后在TSF应用管理中显示刚创建的provider-demo和consumer-demo应用

4)上传provider-demo和consumer-demo应用程序包到对应的应用中
在 应用管理列表页 ，单击目标应用的ID/应用名，进入应用详情页。
在应用详情页的上方，单击程序包管理标签页，单击【上传程序包】按钮

上传成功后可在列表中看到应用信息；


5)创建provider-demo和consumer-demo部署组
在 应用列表，单击新建应用的 ID，进入应用详情页=> 部署组标签页。
在应用详情页=> 部署组标签页的上方，单击【新建部署组】按钮。
设置部署组相关信息。
部署组名称：部署组的名称，不超过 60 个字符。
集群：选择希望建立部署组的集群。可以选择上文建立好的集群。
命名空间：选择希望建立部署组的命名空间。可以选择上文中建立好的命名空间。
日志配置项：应用的日志配置项用于指定 TSF 采集应用的日志路径。
单击【提交】按钮

按上面的步骤创建Provider-demo和consumer-demo部署组；创建完成后可以在部署组列表中查看，显示如下：

6)在部署组中添加实例
在部署组列表的右侧单击【更多】-》【添加实例】；每个部署组各添加一个实例

Provider-demo和consumer-demo部署组各添加一个实例，添加完成后如下：

7)部署应用
在部署组列表页的右侧，单击【部署应用】，选择对应的程序包，修改启动参数【注意虚拟机需要设置的内存大小】，点击提交按键完成部署。

部署成功后显示如下图：

2.4  实验结果检验
1)部署组中可以查看应用的状态为“运行中”

2)浏览器访问服务消费者对应API可以看到结果
部署组列表中点击【conumser-demo】进入查看服务实例，获取服务实例的ip地址：


根据获取的ip地址在浏览器中访问对应服务，如：http://134.175.152.135:18083/echo-rest/hello

访问http://134.175.152.135:18083/echo-feign/hello-feign：

2.5  实验结果
是否已完成本节实验，请将实验结果记录如下：    □ 是      □ 否
                                                                               
                                                                               




















实验 3：使用TSF容器部署微服务
3.1  实验环境
1.虚拟机软件VMWare WorkStations 
2.虚拟机网卡：NAT（需要联网）
3.虚拟机操作系统：CentOS-7-x86_64-Everything-1708.iso
4.要求能够访问腾讯云官网https://cloud.tencent.com
5.要求有腾讯云账号，并且已经开通腾讯微服务平台TSF权限
6.实验过程中需要购买云虚拟机
7.实验过程中涉及的项目jar包使用“实验 2”中开发的应用的jar包
3.2  实验介绍
本实验将带领学员在TSF平台上，使用Docker容器部署微服务。实验分为两个阶段：
第一阶段：Docker的基础实验
第二阶段：使用TSF容器部署微服务
3.3  Docker基础实验
3.3.1  实验介绍
Docker 是一个开源的应用容器引擎，可以让开发者打包他们的应用以及依赖包到一个轻量级、可移植的容器中，然后发布到任何流行的 Linux 机器上，也可以实现虚拟化。
Docker 项目的目标是实现轻量级的操作系统虚拟化解决方案。 用户操作 Docker 的容器就像操作一个快速轻量级的虚拟机一样简单。
本实验首先带领学员安装docker环境，然后在docker环境中快速部署镜像，帮助学员快速掌握docker的基本使用。

3.3.2  实验步骤
【Step 1】安装与验证Docker环境
虚拟机中在线安装Docker
在线安装Docker：yum install docker
启动Docker服务：systemctl start docker.service
开机启动Docker：systemctl enable docker.service
确定Docker的版本：docker version

【Step 2】使用Docker快速部署镜像
使用Docker（Dockerfile）快速构建Docker镜像，并部署一个简单的Web应用。Dockerfile是一个文本文件，其中包含了若干条指令，这些指令描述了构建镜像的细节。
1)准备一个文件，名叫：Dockerfile

该Dockerfile非常简单，其中的FROM、RUN都是Docker的指令。
FROM指令用于指定基础镜像，RUN指令用于执行命令。
2)创建Docker镜像
在Dockerfile所在的目录下，执行命令用于构建镜像：
docker build -t nginx:my .
其中，命令最后的点（.）表示当前路径。输出如下：

使用命令：docker image ls，查看本地Docker的镜像


3)使用Docker镜像创建容器：
docker run -d -p 7788:80 nginx:my
4)使用浏览器访问宿主机的7788端口，可以看到如下界面：

3.3.3  实验结果检验
1)使用浏览器访问宿主机的7788端口（如：http://192.168.32.150:7788）能看到输出“TFS Demo Application and Docker”内容。



3.3.4  实验结果
是否已完成本节实验，请将实验结果记录如下：    □ 是      □ 否
                                                                               
                                                                               

















3.4  使用TSF容器部署微服务
3.4.1  实验介绍
在了解了docker的基本使用之后，接下来将学习TSF容器的基本操作；本实验将帮助学员快速掌握在TSF中使用容器部署微服务应用。
本实验涉及的微服务应用将使用“实验 2”中的provider-demo-0.0.1-SNAPSHOT.jar 和consumer-demo-0.0.1-SNAPSHOT.jar 

3.4.2  实验步骤
【Step 1】创建容器集群
新建容器集群。首先您需要创建容器集群。集群是容器运行所需云资源的集合，包括若干台云主机等。
1)登录TSF控制台：https://console.cloud.tencent.com/tsf/index
2)单击左侧导航栏中的【集群】，单击集群列表上方的【新建集群】。 


3)设置集群的基本信息 
集群名称：集群名称，不超过 60 个字符。
集群类型：选择集群类型，选择 容器集群。
集群网络：为集群内主机分配在云主机网络地址范围内的 IP 地址
容器网络：为集群内容器分配在容器网络地址范围内的 IP 地址
4)单击 【提交】 按钮。容器集群创建到可实际使用需要等待几分钟，直到集群状态变为运行中。

【Step 2】导入云主机
集群中导入云主机后才能生产 Docker 容器。前提条件：集群的 VPC下存在可用的云主机。
1)在集群列表页中，单击目标集群 【ID/集群名】 。
2)单击云主机列表上方的 【导入云主机】 按钮。
3)从集群列表中选择可用的，选择需要添加到集群的云主机。

4)云主机配置
登录方式：提供三种对应登录方式。
设置密码：请根据提示设置对应密码。
立即关联密钥：密钥对是通过一种算法生成的一对参数，是一种比常规密码更安全的登录云服务器的方式。
自动生成密码：自动生成的密码将通过站内信发送给您。
安全组：安全组具有防火墙的功能，用于设置云主机 CVM 的网络访问控制。导入的云主机将出现在云主机名列表中。等待几分钟，刷新列表，正常情况下云主机的状态将变为运行中，可用状态变为可用。 

按照上述导入虚拟机部署导入二台云虚拟机到集群中

【Step 3】创建容器应用
1)登录TSF控制台。
2)单击左侧导航栏应用管理。
3)在应用列表上方单击【新建应用】。

4)设置应用信息后，单击【提交】按钮。
部署方式：选择容器部署
应用类型：选择普通应用
5)按照上述创建容器应用步骤创建provider-demo-container和consumer-demo-conta容器应用；创建完成后显示如下：

【Step 4】初始化镜像仓库
首次使用镜像仓库时，需要进行初始化操作，设置登录仓库的密码。

【Step 5】创建镜像和推送镜像到仓库
1)创建provider-demo应用镜像
在Docker基础实验中已经安装好docker环境的虚拟机中新建目录：/opt/tsf-demo/provider；文件夹中内容如下：

编写Dockerfile，Dockerfile 内容如下：


在 Dockerfile中最后一行CMD命令中，执行了run.sh 脚本。run.sh脚本有如下作用：
启动 jar 包
读取通过 TSF 控制台设置的 JVM 启动参数
将 stdout 数据打印到文件中，用于 TSF 控制台展示。
run.sh不是必须的文件，如果用户不需要通过控制台来设置JVM启动参数设或者显示 stdout日志，可以不使用run.sh脚本。
provider-demo-0.0.1-SNAPSHOT.jar 为“实验 2”中provider-demo项目编译打包后的fatjar文件
run.sh内容如下：


使用 Dockerfile 创建provider-demo的镜像：在 Dockerfile 所在目录执行 build 命令。



2)创建consumer-demo应用镜像
在Docker基础实验中安装好docker环境的虚拟机中新建目录：/opt/tsf-demo/consumer；文件夹中内容如下：

创建Dockerfile，Dockerfile 内容如下：
在 Dockerfile中最后一行CMD命令中，执行了run.sh 脚本。run.sh脚本有如下作用：
启动 jar 包
读取通过 TSF 控制台设置的 JVM 启动参数
将 stdout 数据打印到文件中，用于 TSF 控制台展示。
run.sh不是必须的文件，如果用户不需要通过控制台来设置JVM启动参数设或者显示 stdout日志，可以不使用run.sh脚本。
consumer-demo-0.0.1-SNAPSHOT.jar 为“实验 2”中consumer-demo项目编译打包后的fatjar文件
run.sh内容如下：

使用 Dockerfile 创建consumer-demo的镜像：在 Dockerfile 所在目录执行 build 命令。



镜像创建完毕后执行：docker images 查看镜像，显示如下：

在TSF应用管理-应用列表中点击进入对应的应用可以查看使用指引。


根据使用指引中说明，把镜像推送到TSF应用镜像中。推送成功后可以在应用的镜像标签中查看推送的镜像，显示如下：


【Step 6】创建部署组
1)登录TSF控制台。
2)单击左侧导航栏中的 部署组。
3)选择部署组所属集群和所属命名空间。
4)单击部署组列表上方的【新建部署组】按钮。

5)设置部署组相关信息：

部署组名称：部署组的名称，不超过 60 个字符。
关联应用：选择部署组关联的应用。
实例资源限制：分配给单个实例使用的 CPU 核数和内存资源上限值。
实例数量：一个实例由相关的一个容器构成，可单击 + 和 - 控制实例数量。实例数和实例资源限制的乘积不能超过集群剩余的可用资源。
网络访问方式: 网络的访问方式决定了部署组内应用的网络属性，不同访问方式的应用可以提供不同网络能力。
NodePort: 访问方式不绑定外网负载均衡，在集群内所有主机自动分配 NodePort 端口，可通过 集群内任意主机IP + NodePort 访问该服务。
公网：访问方式自动绑定外网负载均衡，可通过外网负载均衡地址访问该服务。
集群内访问：访问方式不绑定外网负载均衡，该服务只能在集群内部访问。
端口映射：容器端口与服务端口的映射关系。
协议：TCP、UDP。
容器端口：容器内应用程序监听的端口。
服务端口：集群外通过负载均衡域名或IP+服务端口访问服务；集群内通过服务名+服务端口访问服务。建议和容器端口保持一致。
更新方式：选择部署的更新方式。
快速更新：直接关闭所有实例，启动相同数量的新实例。
滚动更新：对实例进行逐个更新，这种方式可以让您不中断业务实现对服务的更新。
日志配置项：选择日志配置项，用于采集应用的业务日志数据。
新建provider-demo-container部署组，部署组信息如下：

新建consumer-demo-container部署组，部署组信息如下：


6)2个部署组新建完成后显示如下：

【Step 7】部署应用
1)在部署组页面中，单击目标部署组右侧的【部署应用】按钮。
2)设置部署相关信息
选择镜像：选择要部署的镜像。
单实例资源限制：分配给单个实例使用的CPU核数和内存资源上限值。
实例数量：一个实例由相关的一个容器构成，可单击 + 和 - 控制实例数量。实例数和实例资源限制的乘积不能超过集群剩余的可用资源。
启动参数（选填）：设置Java应用的启动参数。
Provider-demo-container部署组部署信息如下：

Consumer-demo-container部署组部署信息如下：

3)单击【提交】按钮。部署成功后显示如下：

【Step 8】浏览器访问
1)根据上一步骤中的负载均衡ip访问consumer服务：http://193.112.234.82:18083/echo-rest/hello-docker

2)浏览器访问：http://193.112.234.82:18083/echo-feign/hello-docker


3.4.3  实验结果检验
1)在部署组列表可以获取应用的负载均衡ip：

2)浏览器访问：http://193.112.234.82:18083/echo-rest/hello-docker

3)浏览器访问：http://193.112.234.82:18083/echo-feign/hello-docker




3.4.4  实验结果

实验结果：
是否已完成本节实验，请将实验结果记录如下：    □ 是      □ 否
                                                                               
                                                                               





















实验 4：TSF运维管理
4.1  实验环境
1.要能够访问腾讯云官网https://cloud.tencent.com
2.要求有腾讯云账号，并且已经开通腾讯微服务平台TSF权限
3.实验过程中需要购买云虚拟机


4.2  TSF日志服务
4.2.1  实验介绍
日志服务为用户提供一站式日志服务，从日志采集、日志存储到日志内容搜索，帮助用户轻松定位业务问题。用户通过指定部署组的日志配置项来指定日志采集规则，TSF Agent 根据日志配置项采集指定路径下的文件日志，并上传日志到日志存储模块。用户可以通过 TSF 控制台查看部署组实时日志，并根据关键词来检索日志。
下图是日志服务原理：

TSF 平台上使用日志服务的流程图：

4.2.2  实验步骤
【Step 1】项目配置文件中添加日志配置
1)此实验代码开发基于“实验 2”中创建的服务提供者（provider-demo）和服务消费者（consumer-demo）代码；在provider-demo和consumer-demo项目中的application.yml配置文件中各自添加如下配置：

此处“${spring.application.name}”是一个表达式，表示引用spring.application.name这个配置项内容；

【Step 2】部署微服务到TSF平台
把provider-demo和consumer-demo重新打包，按照“实验二”步骤把provider-demo和consumer-demo应用部署到TSF上。
【Step 3】新建日志配置项并发布到部署组
1)在“配置管理--日志配置”中新建配置项：

2)新建provider-demo日志配置项：

3)新建consumer-dmeo日志配置项：

4)2个日志配置项新建完成后可以在列表查看：

5)点击provider-demo日志配置项列表右侧的“发布配置”，发布provider-demo日志配置项到provider-demo部署组；

6)点击consumer-demo日志配置项列表右侧的“发布配置”，发布consumer-demo日志配置项到consumer-demo部署组：

7)配置发布以后可以点击列表右侧“查看发布情况”查看发布情况：



【Step 4】部署组中查看日志
1)在部署组右侧的“更多”下列列表中选择“查看日志”可以查看当前部署组的日志信息：

2)选择刚发布的“consumer-demo”日志配置项，可以查看日志：

3)开启右侧的“自动刷新”功能后可以查看实时日志，默认情况下已开启，可以切换查看对应时间段的日志：

4)可以在条件框中输入搜索条件检索日志：


4.2.3  实验结果检验
1)可以在部署组列表的“更多”选项中选择对应的日志配置项：

2)选择日志配置项后可以查看到日志：


4.2.4  实验结果
是否已完成本节实验，请将实验结果记录如下：    □ 是      □ 否
                                                                               
                                                                               
4.3  TSF调用链查询
4.3.1  实验介绍
调用链查询用来查询和定位具体某一次调用的情况。使用者可以通过具体的服务、接口定位、IP 等来查询具体的调用过程，查找调用过程所需要的时间和运行情况。
4.3.2  实验步骤
【Step 1】添加调用链依赖
1)此实验代码基于4.2章节中的provider-demo和consumer-demo项目代码。在provider-demo和consumer-demo项目各自的pom.xml中添加如下依赖

代码截图如下：	

【Step 2】部署微服务到TSF平台
把provider-demo和consumer-demo重新打包，按照“实验二”步骤把provider-demo和consumer-demo应用部署到TSF上。
【Step 3】浏览器访问微服务，查询调用链
1)通过浏览器访问consumer-demo服务，访问地址如下：
http://consumer部署组IP:18083/echo-rest/test

2)访问consumer-dmeo服务会调用provider-dmeo服务，访问后可以在“服务依赖拓扑”中查看服务的依赖关系，如下图：

3)点击左侧“调用链查询”可以查看刚刚访问的服务的调用情况：


4.3.3  实验结果检验
1)浏览器访问http://consumer部署组IP:18083/echo-rest/test后可以查看服务的依赖拓扑信息和调用链。
依赖拓扑信息：

调用链信息：

点击调用链列表对应的“Trace ID”可以查看具体的调用信息：

4.3.4  实验结果
是否已完成本节实验，请将实验结果记录如下：    □ 是      □ 否
                                                                               
                                                                               


4.4  TSF弹性伸缩
4.4.1  实验介绍
弹性伸缩含义：根据预先设定的弹性伸缩规则，动态增加或者减少部署组的实例数。
弹性伸缩规则：由规则名、扩容活动、缩容活动、冷却时间等参数构成的规则，用来描述弹性扩缩容的触发条件、实例数量变化和限制。
弹性伸缩指标：
1)CPU 利用率：在指定时间范围内，部署组内所有实例 CPU 利用率的平均值。
2)内存利用率：在指定时间范围内，部署组内所有实例内存利用率的平均值。
3)请求 QPS：在指定时间范围内，部署组内所有实例请求 QPS 的平均值。
4)响应时间：在指定时间范围内，部署组内所有实例响应时间的平均值。
冷却时间：设置冷却时间，可以确保在上一扩（缩）容活动生效前弹性伸缩不会启动或终止其他实例。弹性伸缩会等待冷却时间完成，然后再继续扩（缩）容活动。建议设置冷却时间大于持续时间。
本实验通过模拟CPU的高使用量触发弹性伸缩场景。
4.4.2  实验步骤
【Step 1】创建弹性伸缩规则
1)点击TSF控制台左侧的“弹性伸缩”菜单新建弹性伸缩规则：

2)填写弹性伸缩规则：

3)创建完成后列表展示如下图：

【Step 2】关联部署组
1)点击弹性伸缩规则列表右侧的“关联部署组”链接，把弹性伸缩规则关联到部署组：

2)点击对应的弹性伸缩规则后可以查看关联的部署组信息：



【Step 3】集群中导入云虚拟机
1)弹性伸缩需要在集群中导入用来扩容的云虚拟机，如下图，列表中第一个集群就是用来做扩容的，第二个，第三个正着运行当前的provider-demo和consumer-demo应用:


【Step 4】触发弹性伸缩规则查看效果
1)为了让系统自动扩容，我们现在手工模拟系统负载量的增大；在云服务器控制台上登录到provider-demo服务所在的CVM 上，IP地址可以在部署组中查看。

2)登录到如上云虚拟机，执行以下命令（CPU占用率达到99%）：


3)输入如上命令后可以使用： top -bn 1 -i -c 查看CPU使用率，可以发现次数CPU的占用率达到了99%以上：

4)等待几分钟后会触发扩容活动：触发扩容活动前provider-dmeo的运行情况如下：

5)触发扩容活动后，部署组的运行情况如下，自动扩容了一台机器：

6)把上一步中高CPU占用的线程停止：



7)等待几分钟后，CPU占用率下降到50%以下，触发弹性伸缩中的缩容活动，部署组中运行的2个实例会变为一个：

4.4.3  实验结果检验
1)根据弹性伸缩规则能够自动触发部署组的扩容以及缩容活动。具体截图见实验步骤


4.4.4  实验结果
是否已完成本节实验，请将实验结果记录如下：    □ 是      □ 否
                                                                               
                                                                               


实验 5：使用TSF服务治理
5.1  实验环境
1.Java软件开发工具包JDK安装
(1)版本：1.8
2.Maven安装：
(1)版本：3.5及以上
(2)修改setting配置为国内下载镜像
(3)Maven编码过程中需要下载依赖，需要网络环境
3.Eclipse或者IDEA，此实验采用Eclipse作为开发工具
4.能够访问腾讯云官网https://cloud.tencent.com
5.确保已完成了TSF的 SDK 下载；详情参考：https://cloud.tencent.com/document/product/649/20231

5.2  实验介绍
本小节旨在帮助大家掌握如何使用TSF的应用服务自治功能，包括以下实验内容：
服务基本操作
使用服务鉴权功能
使用服务限流
使用服务路由
5.3  服务治理开发准备
此实验代码开发基于“实验 2”中创建的服务提供者（provider-demo）和服务消费者（consumer-demo）代码；
1)在provider-demo和consumer-demo的根目录pom.xml配置文件的dependencies节点中添加依赖（依赖说明请看代码注释）：

2)在provider-demo和consumer-demo项目中的各自启动类中添加启动注解（代码中红色部分内容）

3)为了支持自定义标签，在consumer-demo项目中的Controller.java代码中添加设置自定义标签代码（下图二中红色字体部分）：


4)参考“实验 2 使用TSF虚拟机部署微服务应用”把开发后的应用部署到TSF控制台。
5.4  服务基本操作
5.4.1  实验介绍
5)服务是微服务平台管理的基本单元。服务管理包括服务的生命周期管理和服务治理两部分。服务基本操作包括创建服务和删除服务。本实验主要带领学员掌握服务的创建和删除操作。
5.4.2  实验步骤
【Step 1】创建服务
1)登录TSF控制台（https://console.cloud.tencent.com/tsf/index）。
2)单击左侧导航栏的 服务治理，选择集群和命名空间。
3)单击服务列表页的【新建】。

4)设置服务的基本信息后，单击【提交】按钮。
服务名称：要创建的服务的名称，不超过60个字符。服务名称由小写字母、数字和 - 组成，且由小写字母开头，小写字母或数字结尾。
服务描述：服务的描述信息。

【Step 2】删除服务
1)单击服务列表右侧【删除】。

2)在弹框中单击【确认】按钮，注意：只有当服务运行的实例数为0时，可删除服务。

5.4.3  实验结果检验：
1)能够在TSF控制台的服务治理功能中完成服务创建和服务删除功能即可。
5.4.4  实验结果
是否已完成本节实验，请将实验结果记录如下：    □ 是      □ 否
                                                                               
                                                                               


5.5  使用服务鉴权功能
5.5.1  实验介绍
服务鉴权是处理微服务之间相互访问权限问题的解决方案。配置中心下发鉴权规则到服务，当请求到来时，服务根据鉴权规则判断鉴权结果，如果鉴权通过，则继续处理请求，否则返回鉴权失败的 HTTP 状态码 403（Forbidden）。
鉴权流程如下：


服务鉴权功能支持白名单和黑名单两种鉴权方式。
白名单：当请求匹配任意一条鉴权规则时，允许调用；否则拒绝调用。
黑名单：当请求匹配任意一条鉴权规则时，拒绝调用；否则允许调用
本实验主要帮助学员掌握基础的鉴权操作。
5.5.2  实验步骤
【Step 1】进入服务治理-服务鉴权控制页面
1)登录TSF控制台。
2)单击左侧导航栏 服务治理。
3)单击服务列表的服务名，进入服务详情页。

【Step 2】新建服务鉴权规则并生效
1)在服务详情的【服务鉴权】标签页，选择鉴权方式
a.不启用：关闭鉴权功能。
b.白名单：匹配任意一条规则的请求，允许调用。
c.黑名单：匹配任意一条规则的请求，拒绝调用。

2)选择白名单或黑名单，在下方单击【新建鉴权规则】按钮，在新建页面中填写规则信息，并选择规则的【生效状态】，点击【完成】


【Step 3】查看服务鉴权效果
1)检查鉴权效果：
点击进入consumer-demo服务，查看consumer-demo应用的公网IP

2)查看Api列表如下：

3)在浏览器中访问上面任一api：例如：http://129.204.13.123:18083/echo-rest/test（此处连接最后的test为参数，跟鉴权规则中的user的值test对应，只有此处参数为“test”才可以正常访问得到结果）


5.5.3  实验结果检验
1.能够获取consumer-demo服务ip以及API列表：
(1)进去consumer-demo服务，查看consumer-demo应用的公网ip

(2)点击consumer-demo服务的API列表标签页可以查看服务的API列表

2.可以查看服务鉴权效果
(1)在浏览器中访问上面任一api：例如：http://129.204.13.123:18083/echo-rest/test
此处连接最后的test为参数，跟鉴权规则中的user的值test对应，
只有此处参数为“test”才可以正常访问得到结果，否则出现下图二结果





5.5.4  实验结果

是否已完成本节实验，请将实验结果记录如下：    □ 是      □ 否
                                                                               
                                                                               



















5.6  使用服务限流功能
5.6.1  实验介绍
服务限流主要是保护服务节点或者数据节点，防止瞬时流量过大造成服务和数据崩溃，导致服务不可用。当资源成为瓶颈时，服务框架需要对请求做限流，启动流控保护机制。
服务限流原理：
在服务提供者端配置限流依赖项，在 TSF 控制台配置限流规则。此时服务消费者去调用服务提供者时，所有的访问请求都会通过限流模块进行计算，若服务消费者调用量在一定时间内超过了预设阈值，则会触发限流策略，进行限流处理。
TSF 限流方案采用了动态配额分配制，限流中控根据实例的历史流量记录，动态计算预测下一时刻该实例的流量，若所有实例的流量预测值都小于额定平均值（总配额/在线实例数），则以该平均值作为所有实例分配的配额；否则按预测流量的比例分配，且保证一个最小值。

5.6.2  实验步骤
【Step 1】进入服务限流标签页
1)登录 TSF 控制台。
2)单击左侧导航栏 服务治理。
3)单击服务列表的服务名，进入服务详情页。

【Step 2】新建限流规则
1)选择服务限流标签页，单击【新建限流规则】按钮。填写限流规则信息
规则名：填写规则名
限流阈值：
主调服务：可以选择某一个服务或者所有服务。如果选择单个服务，则只针对单个主调服务的请求进行限流；如果选择所有服务，则针对所有请求进行限流。
单位时间：单位是秒，正整数
请求数：正整数
生效状态：是否立即启用限流规则
描述：填写描述信息

2)单击【完成】按钮。

【Step 3】查看限流效果
1)检验限流效果：如果请求数达到了限流阈值，任何到达的请求都会限流模块处理。如果该服务上的配额已经消耗完，会对请求返回HTTP 429 Too Many Requests；否则会正常放行。用户可以在限流规则列表下方的 请求数-时间 图中查看到被限制的请求数或者 被限制请求率-时间 图中查看到被限制请求率（计算公式 被限制请求率 = 被限制的请求数 / 请求数）随时间的变化。
进去consumer-demo服务，查看consumer-demo应用的公网ip

查看API列表

在浏览器中访问上面任一api：例如：http://129.204.13.123:18083/echo-rest/test
当快速刷新页面的时候，超过1秒1次的时候将返回429异常码）
正常访问：

超出访问限制出现429异常码：

限流规则下方可以查看限流效果图表：

5.6.3  实验结果检验
1)能够获取consumer-demo服务ip以及API列表：
(1)进去consumer-demo服务，查看consumer-demo应用的公网ip

(2)点击consumer-demo服务的API列表标签页可以查看服务的API列表

2)可以查看限流效果
(3)在浏览器中访问上面任一api：例如：http://129.204.13.123:18083/echo-rest/test
当快速刷新页面的时候，超过1秒1次的时候将返回429异常码）
正常访问：

超出访问限制出现429异常码：

限流规则下方可以查看限流效果图表：






5.6.4  实验结果

是否已完成本节实验，请将实验结果记录如下：    □ 是      □ 否
                                                                               
                                                                               



















5.7  使用服务路由功能
5.7.1  实验介绍
用户在使用 TSF 运行自己的业务时，由于业务的复杂程度，常常需要部署数目庞大的服务运行在现网环境中。这些服务运行在属性不同的实例上、部署在不同的地域中，用户经常需要选择符合自己特定要求的属性来选择服务的提供者，对服务间流量的分配起到掌控的作用。
同时，在微服务的场景下，用户研发新版本上线的迭代周期越来越快，稳定敏捷的上线新版本需要微服务框架能够支持灰度发布、金丝雀发布、滚动发布等发布方式。通过服务路由功能，用户可以配置流量分配权重，设置某些权重的流量被分配到某个版本号中，为灰度发布等上线模式提供了无需终止服务的底层能力支持。为了保证满足客户的定制化需求，TSF 支持用户定制自己的路由标签，并支持选择不同的逻辑形式配置标签值，定向分配流量。 总而言之，服务路由功能的主要作用是将调用流量按照自己的需求进行分配。
TSF 配置服务路由功能支持以下三种配置方式：
按照权重方式配置路由规则。在需要配置服务路由的服务下面，用户可以选择配置流量的权重，将部分权重的请求流量分配到服务提供方的某个版本下或某个部署组下。
按照系统自带标签的方式进行配置路由规则。每一个 TSF 上运行的服务都已经被预先设置好了某些标签，如发起请求的服务消费方所在的部署组、IP、服务发起方的版本号等等。用户可以选择这些标签，并配置标签值的特定规则，分配带有某些流量的标签到服务提供方的某个部署组上进行处理。在标签值的配置上，用户可以填写“包含、不包含”，“等于、不等于”，正则表达式等等灵活的规则。
按照用户自定义的标签进行配置路由规则。TSF提供了用户配置自定义标签的SDK，在实际的使用中，如果系统自带标签不能保证用户使用的场景，用户可以自定义标签内容，在SDK中进行配置，并在控制台上配置相同的标签，控制服务消费方提供的流量按照配置的方式流入服务提供方。
5.7.2  实验步骤
【Step 1】新建灰度发布部署组
1)安装“实验 2”说明，新增一个部署组“provider-demo-gray”关联到“provider-demo”应用,并添加实例

2)在“provider-demo-gray”部署组中部署“provider-dem”应用，确保如下3个部署组都启动了

【Step 2】进入服务路由管理
1)登录TSF控制台。
2)选择左侧菜单中【服务治理】菜单项。

3)选择进去“provider-demo”服务，进去“服务路由”选项卡。

【Step 3】新建路由规则
1)单击【新建路由规则】。

选择路由规则类型。现有的路由规则分为三种。
基于权重的路由规则可以实现将某些百分比的流量分配到某个版本、部署组上，这里面可选择的版本号和部署组是该服务绑定应用下的版本号和部署组列表。
基于系统自带标签的路由规则可以支持选择服务消费方的版本号、部署组、IP、应用 ID，用户选择标签与标签值的匹配方式，并填写标签值的内容。其中，匹配方式可以参考【服务鉴权】中标签鉴权的匹配规范。系统自带标签路由规则解决的问题是，将带有一定系统自带标签的请求，发送到目标的部署组上。
基于自定义标签的路由规则。用户在后台配置自定义标签，并在控制台上填写标签名称。其他填写内容同系统自带标签相同。设置自定义标签的过程请参考 服务鉴权。
2)使规则生效

【Step 4】查看路由效果
1)进入consumer-demo服务，查看consumer-demo应用的公网ip

2)Api列表如下：

3)在浏览器中访问上面任一api：例如：http://129.204.13.123:18083/echo-rest/test
连续快速刷新页面，连续访问5分钟
访问结果：

在路由规则下方查看路由分布流量统计，注意统计结果有延迟，需要等待几分钟后查看效果图


5.7.3  实验结果检验
1.能够获取访问地址并能够查看API：
(1)进入consumer-demo服务，查看consumer-demo应用的公网ip

(2)点击consumer-demo服务的API列表标签页可以查看服务的API列表

2.页面能够正常访问，能够查看流量分布图表展示结果；
(1)访问上面任一api：例如：http://[consumer-demo实例公网IP]:18083/echo-rest/test
(2)连续快速刷新页面，连续访问5分钟
(3)正常访问结果：

(4)在路由规则下方查看路由分布流量统计，注意统计结果有延迟，需要等待几分钟后查看效果图


3.如果关闭“provider-demo-gray”部署组：访问将在正常、异常循环出现
停止“provider-demo-gray”部署组

路由到provider-demo将正常显示结果

路由到provider-demo-gray将显示异常结果

5.7.4  实验结果

是否已完成本节实验，请将实验结果记录如下：    □ 是      □ 否
                                                                               
                                                                               














实验 6：使用TSF配置管理功能
6.1  实验环境
1.Java软件开发工具包JDK安装
(1)版本：1.8
2.Maven安装：
(1)版本：3.5及以上
(2)修改setting配置为国内下载镜像
(3)Maven编码过程中需要下载依赖，需要网络环境
3.Eclipse或者IDEA，此实验采用Eclipse作为开发工具
4.能够访问腾讯云官网https://cloud.tencent.com
5.确保已完成了TSF的 SDK 下载；详情参考：https://cloud.tencent.com/document/product/649/20231
6.2  实验介绍
为了更好地解决分布式环境下多台服务实例的配置统一管理问题，TSF 提供了分布式配置功能。TSF 的配置功能具有如下特点：
支持配置的分布式化管理
配置统一化发布
配置更新自动化：用户在平台更新配置，使用该配置的系统会自动发现该情况，并应用新配置。
配置异构系统管理：异构系统是指一个应用有多个部署组时（例如开发环境、测试环境的部署组），由于配置不同，从而需要多个部署包的情况。使用分布式配置功能后，用户只需要同一个部署包，不同部署组的配置会自动分配。
支持在控制台上编写 YAML 格式的配置。
应用配置、全局配置、本地配置优先级
应用配置和全局配置属于 TSF 平台上的配置（下面称为远程配置），本地配置是应用程序在代码工程中创建的配置（如 application.yml 和 bootstrap.yml）。应用配置和全局配置的根本区别在于配置发布的范围，应用配置发布的范围是部署组维度，全局配置发布的范围是命名空间维度。
优先级：应用配置 > 全局配置 > 本地配置
多份应用配置发布到同一个部署组
TSF 支持多份应用配置发布到同一个部署组，多份配置会根据发布时间的先后顺序以 key 来进行合并
6.3  配置管理开发准备
此实验代码开发基于“实验 2”中创建的服务提供者（provider-demo）和服务消费者（consumer-demo）代码；
1)在provider-demo项目根目录的pom.xml中添加依赖：

2)启动类上添加注解，如下代码中红色部分：

3)添加配置映射类ProviderNameConfig.java


ProviderNameConfig.java代码如下：

4)修改provider-demo项目中的EchoController.java类（红色字体部分代码为新增的代码）：


5)重新编译打包provider-demo，把打包后的fatjar上传到TSF控制台对应应用中（按照“实验 2”中说明把provider-demo部署到TSF控制台）

6)选择最新的provider-demo程序版本进行部署：

7)部署成功后状态显示：

6.4  实验步骤
【Step 1】新建应用配置
1)创建配置：
登录 TSF 控制台。
在左侧导航栏，单击【配置管理】>【应用配置】。
在应用配置页面顶部，选择目标应用。
在配置列表标签页，单击【新建】按钮。

【Step 2】填写应用配置内容
1)填写配置内容。
配置项名：填写配置名。
配置内容：按照 YAML 格式。YAML 格式规范参考 YAML 格式介绍。
版本号：填写初始版本号。
版本描述：填写初始版本的描述。
此处配置项的值为开发准备中添加的配置项映射类中的配置映射字段




2)完成配置项添加以后需要发布配置项到对应的部署组；在未发布配置项前先访问consumer-demo项目的api接口查看返回内容
获取consumer-demo公网ip

浏览器访问接口：http://203.195.252.211:18083/echo-rest/hello
其中echo-provider-default-name 为原配置项中默认值（ProviderNameConfig.java中name的默认值）

【Step 3】发布配置
1)发布配置项到provider-demo部署组
点击进入配置项:


点击发布配置项到provider-demo部署组：

查看部署组中发布的配置


【Step 4】浏览器查看配置内容
1)浏览器访问consumer-demo项目接口：
获取consumer-demo公网ip

浏览器访问接口：http://203.195.252.211:18083/echo-rest/hello

此时前面的配置项的值就变为了“provider-demd”值，跟我们在配置项中发布出来的配置一致

6.5  实验结果检验
1)发布配置项后，使用浏览器访问consumer-demo项目接口；能在浏览器中输出更新后的配置项内容：
发布的配置内容：

浏览器访问获取的配置项的值


6.6  实验结果
是否已完成本节实验，请将实验结果记录如下：    □ 是      □ 否
                                                                               
                                                                               
























实验 7：TSF与API网关使用
7.1  实验环境
1.要能够访问腾讯云官网https://cloud.tencent.com
2.要求有腾讯云账号，并且已经开通腾讯微服务平台TSF权限
3.实验过程中需要购买云虚拟机
4.Postman调试工具


7.2  实验介绍
用户在腾讯云 API 网关上可以通过配置微服务 API，将外部请求转发到 TSF 平台上部署的微服务。API 网关与 TSF 平台内微服务的关系如下：

腾讯云 API 网关与腾讯云 TSF 是两个独立的产品。
用户必须给命名空间配置 Code（Namespace Code），才能将 API 网关外部的请求转发到命名空间内的微服务。对于在不同命名空间内且名称都是 product 的微服务，只有通过命名空间 ID 和微服务名称来唯一确定一个微服务。由于平台生成的命名空间 ID 较难辨识（通常是 namespaceid-xxxx），因此需要引入命名空间 Code 作为唯一标识。命名空间 Code 具有如下特性：跨集群唯一性、用户自定义、可读性强、不可修改

7.3  实验步骤
【Step 1】在 TSF 上配置命名空间 Code
在左侧导航栏单击 命名空间，进入命名空间列表页。选择目标集群后，单击命名空间列表右侧【设置Code】


【Step 2】API网关配置微服务API
1)进入腾讯云API网关：

2)创建API服务：


3)创建完成后列表如下图：

4)点击上图中的consumer_demo服务进入API设置：

5)新建微服务API:


6)点击下一步进入后端配置：

7)点击下一步进入响应结果配置：

8)点击完成后可以在API列表查看：

【Step 3】调试微服务API
1)点击微服务API列表中，右侧的“调调试”链接进行调试

2)输入调试参数，发送请求，查看结果，如果能看到右侧返回结果（返回码200）表示微服务API配置成功。

【Step 3】发布API服务
1)回到API网关的主页面把API发布到对应环境：

2)如下是发布到测试环境中：


【Step 4】Postman模拟访问
1)根据默认域名以及发布环境进行访问：

Postman模拟访问链接：http://service-bii1yea4-1255596198.gz.apigw.tencentcs.com/test/echo-rest/hello
其中service-bii1yea4-1255596198.gz.apigw.tencentcs.com为默认域名
域名后面的test为对应的环境，例如我们是发布到测试环境，所以是test，如果是发布到正式环境则为publish；
echo-rest/hello为访问的地址，跟在微服务API配置中的路径一致
X-MicroService-Name：必填的header参数，表示微服务的名称
X-NameSpace-Code：TSF中对应微服务所在的集群的命名空间code，参考stp1中配置

7.4  实验结果检验
1)如实验步骤中调试步骤，可以在调试步骤中正常获取返回结果
2)Postman中模拟访问能拿到正常的返回结果

7.5  实验结果
是否已完成本节实验，请将实验结果记录如下：    □ 是      □ 否
                                                                               
                                                                               











实验 8：使用TSF分布式事务功能
8.1  实验环境
1.Java软件开发工具包JDK安装
(1)版本：1.8
2.Maven安装：
(1)版本：3.5及以上
(2)修改setting配置为国内下载镜像
(3)Maven编码过程中需要下载依赖，需要网络环境
3.Eclipse或者IDEA，此实验采用Eclipse作为开发工具
4.能够访问腾讯云官网https://cloud.tencent.com
5.实验前请确保已完成了TSF的 SDK 下载

8.2  实验介绍
TSF 提供金融级别高可用分布式事务能力，保证大规模的分布式场景下的业务一致性。TSF 框架下的分布式事务基于 TCC（Try、Confirm 和 Cancel 的简称）模式，支持跨数据库、跨服务的使用场景。为金融、制造业、互联网等行业客户保驾护航。
TCC 模式：
TCC 模式是一种补偿性分布式事务，包含 Try、Confirm、Cancel 三种操作。其中 Try 阶段预留业务资源，Confirm 阶段确认执行业务操作，Cancel 阶段取消执行业务操作。
TCC 模式解决了跨服务操作的原子性问题，对数据库的操作为一阶段提交，性能较好。因此，TCC 模式是现今被广泛应用的一种分布式事务模式。
实验过程采用TSF官网的TCC事务demo案例：
下载地址：https://main.qcloudimg.com/raw/c94b25e89331d419db608565618581f3.zip
本样例是常见的一个线上的代金券/现金协同购物场景。在进行购物的时候，消费用户可以通过使用代金券来抵消一部分的现金费用：用户在消费的时候出示一张2元的代金券，在购买价值20元的物品的时候，只需要从微信钱包中支付18元即可。
在整个购物事务场景中，假设涉及三个的不同子服务：代金券服务、微信钱包服务以及商家账务服务。各个子服务部署在不同的节点上，使用不同的数据库。
本样例展示了如何使用 TCC 来完成一次跨服务/跨数据库的分布式事务。


8.3 实验步骤
【Step 1】下载TCC应用样例
1)下载TSF分布式事务的部署样例
下载地址：https://main.qcloudimg.com/raw/c94b25e89331d419db608565618581f3.zip
下载后解压目录如下：

项目结构如下：

项目说明：
lib：分布式事务需要依赖的 jar 包。
sample-tcc-consumer：消费用户 Client，发起购物事务。
sample-tcc-couponService：代金券子服务，处理代金券使用流程。
sample-tcc-walletService：微信钱包子服务，处理消费用户钱包消费流程。
sample-tcc-transferService：商家账务子服务，转账给对应商家。
sample-tcc-mainService：购物服务，购物事务入口，协调执行3个子服务。
tcc-test-simple：完整的简易 Demo，只需要一台服务器就能运行，无需安装 MySQL。
init_database.sql：初始化样例数据库。
2)下载 Demo 压缩包并解压，在 Demo 的根目录下执行以下命令：mvn clean package
等待 maven 打包成功之后，在以下目录找到 jar 包，用于公有云部署
sample-tcc-couponService/target
sample-tcc-mainService/target
sample-tcc-transferService/target
sample-tcc-walletService/target
tcc-test-simple/target （用于简单部署,如果您只需简单试用，可以只部署 tcc-test-simple，跳过接下来的其它4个应用的部署，也不需要安装mysql服务）

【Step 2】导入云虚拟机到集群
1)在公有云上申请机器，并导入到虚拟机集群中，
注意导入的时候虚拟机的操作系统选择ubuntn；

导入4台虚拟机到集群中

【Step 3】创建对应应用
1)在tsf应用管理中创建tcc事务对应的4个应用

2)把样例中打包后的fatjar应用程序包上传到TSF应用管理中对应的应用中。




3)在tsf部署组中中创建tcc事务对应的4个部署组，并关联对应的应用

4)在每个部署组中添加一个虚拟机实例

【Step 4】安装mysql服务
1)在 mainService 所在虚拟机实例上安装 MySQL 和 ubuntu 服务器（导入虚拟机到集群中直接选择ubuntu操作系统），
进入云服务器


登录到mainService 所在虚拟机实例，


执行如下命令安装mysql服务，安装过程中配置mysql的密码为：“root”

     
配置 MySQL 远程登录权限，并初始化数据库。
    
【Step 5】新建mysql的url全局配置
1)TSF配置管理，全局配置中添加mysql连接url配置

2)填写配置内容：


3)点击进入tcc全局配置

【Step 6】发布全局配置到对应命名空间
1)点击发布按钮，发布应用到对应的命名空间

【Step 7】部署应用
1)在TSF部署组中按照 couponService、transferService，walletService、mainService 的顺序部署应用。

2) 在mainService应用所部署的的机器上，通过 HTTP 请求 mainService。如果 mainService 所在的实例有公网 IP，也可以直接通过公网 IP 访问。

【Step 8】查看结果
1)在TSF事务管理中查看事务结果

点击进入可查看事务详情

8.4  实验结果检验
1)应用部署成功后可以在“服务治理”中查看对应服务，并显示在线

2)服务依赖拓扑中显示服务的关系

3)执行事务访问后在事务管理中能查看事务提交情况








8.5  实验结果
是否已完成本节实验，请将实验结果记录如下：    □ 是      □ 否
                                                                               
                                                                               
###  备注

安全组设置 https://cloud.tencent.com/document/product/213/34601
 