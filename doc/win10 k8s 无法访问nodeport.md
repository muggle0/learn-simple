解决Windows版Desktop Docker中自带的K8s，NodePort无法在本地访问
问题现象
原因分析
解决方案
问题现象
在本地Desktop Docker中启用自带k8s，并部署了服务
在这里插入图片描述在这里插入图片描述

指定了Nodeport端口，在服务容器中crul可访问到服务，但是在宿主机（windows127.0.0.1）通过Nodeport无法访问。
尝试过关闭windows防火墙，重新k8s，重新部署服务均不能够解决问题

原因分析
通过容器直接访问服务可以访问，说明容器服务没有问题，那么问题应该出在容器ip和宿主机ip的绑定上。

先找到指定的pod

PS C:\Users\win10> kubectl get pod
NAME                     READY   STATUS    RESTARTS      AGE
mysql-596b96985c-xf2nb   1/1     Running   2 (46m ago)   87m
myweb-859d5ccfc5-ndmwj   1/1     Running   2 (46m ago)   84m
myweb-859d5ccfc5-sjgnw   1/1     Running   2 (46m ago)   84m
尝试手动绑定宿主机端口到容器kubectl port-forward

$kubectl port-forward myweb-859d5ccfc5-ndmwj 30001:8080
此时发生了报错，报错如下：

An attempt was made to access a socket in a way forbidden by its access permissions。

说明windows端口绑定失败，可能与k8s无关。

解决方案
为了解决上述报错问题，就要先了解 An attempt was made to access a socket in a way forbidden by its access permissions这个错误发生的原因
forbidden by its access permissions
查看当前动态端口配置：

netsh int ipv4 show dynamicport tcp
如果结果是：

启动端口 : 1024
端口数 : 64511

需要进行更改

netsh int ipv4 set dynamicport tcp start=49152 num=16383
netsh int ipv4 set dynamicport udp start=49152 num=16383
再次查看netsh int ipv4 show dynamicport tcp

启动端口 : 49152
端口数 : 16383

重启Desktop Docker
可正常访问服务