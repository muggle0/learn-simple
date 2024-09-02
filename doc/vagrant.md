# 本地开发集群新方案 Virtualbox+Vagrant
vagrant 相对于 vmware 而言更轻量级，操作更简便移植性更强，如果我们需要学习k8s或者搭建一些集群的话建议使用 Virtualbox+Vagrant。
Vagrant 是创建虚拟机的工具，Virtualbox 是vagrant 管理工具，而且这两个软件是开源的，不需要我去付费或者破解。

## 环境准备

vagrant 下载地址：https://www.vagrantup.com
virtualbox 下载地址：https://www.virtualbox.org/wiki/Downloads

下载安装完成后，我们还需要下载vagrant镜像
镜像下载地址：https://app.vagrantup.com/boxes/search

打开cmd 窗口，添加本地镜像：
```
vagrant box add --name 镜像名称 E:data/centos7.box

## 查看所有镜像
vagrant box list 

## 初始化Vagrantfile
vagarnt init 镜像名称
```

执行 `vagarnt init` 指令会根据镜像在当前文件夹生成一个`Vagrantfile`文件，这个文件是创建虚拟机的配置文件。

打开 virtualbox，管理->常规->默认虚拟电脑位置，设置虚拟机存储文件夹。

打开cmd 输入 `ipconfig` 查看VirtualBox 的虚拟网卡ip:

```
以太网适配器 VirtualBox Host-Only Network:

   连接特定的 DNS 后缀 . . . . . . . :
   本地链接 IPv6 地址. . . . . . . . : fe80::85e5:a582:5c5a:bb5b%15
   IPv4 地址 . . . . . . . . . . . . : 192.168.56.1
```

修改 `Vagrantfile`文件，指定其虚拟机的内网ip，需要和虚拟网卡一个网段:
```
### 配置位置可文件中搜索该配置项，有相应的提示
  config.vm.network "private_network", ip: "192.168.56.10"
```
在 `Vagrantfile`所在文件夹打开powershell或者cmd 执行指令`vagrant up` 启动一个虚拟机。

待虚拟机启动完成后执行 `vagrant ssh` 进入虚拟机，该虚拟机的root默认密码为`vagrant`，进入时的账号也是`vagrant`:

```

PS E:\vagrant\cent1> vagrant up
Bringing machine 'default' up with 'virtualbox' provider...
==> default: Clearing any previously set forwarded ports...
==> default: Clearing any previously set network interfaces...
==> default: Preparing network interfaces based on configuration...
    default: Adapter 1: nat
    default: Adapter 2: hostonly
==> default: Forwarding ports...
    default: 22 (guest) => 2222 (host) (adapter 1)
==> default: Booting VM...
==> default: Waiting for machine to boot. This may take a few minutes...
    default: SSH address: 127.0.0.1:2222
    default: SSH username: vagrant
    default: SSH auth method: private key
==> default: Machine booted and ready!
==> default: Checking for guest additions in VM...
    default: No guest additions were detected on the base box for this VM! Guest
    default: additions are required for forwarded ports, shared folders, host only
    default: networking, and more. If SSH fails on this machine, please install
    default: the guest additions and repackage the box to continue.
    default:
    default: This is not an error message; everything may continue to work properly,
    default: in which case you may ignore this message.
==> default: Configuring and enabling network interfaces...
==> default: Rsyncing folder: /cygdrive/e/vagrant/cent1/ => /vagrant
==> default: Machine already provisioned. Run `vagrant provision` or use the `--provision`
==> default: flag to force provisioning. Provisioners marked to run always will still run.
PS E:\vagrant\cent1> vagrant ssh
[vagrant@localhost ~]$ whoami
vagrant
[vagrant@localhost ~]$ ls /
bin   dev  home  lib64  mnt  proc  run   srv       sys  usr      var
boot  etc  lib   media  opt  root  sbin  swapfile  tmp  vagrant
[vagrant@localhost ~]$
```

## 多机启动
我们在搭建一些环境时往往需要启动多台虚拟机，接下来我们将介绍如何创建多台虚拟机并进行相关的配置。

我们修改`Vagrantfile` 内容：

```
Vagrant.configure("2") do |config| 
   (1..3).each do |i| 
       
      #定义节点变量 
      config.vm.define "node#{i}" do |node| 
      
      # box配置 
      node.vm.box = "centos7" 
 
      # 设置虚拟机的主机名 
      node.vm.hostname = "node#{i}" 
 
      # 设置虚拟机的IP 
      node.vm.network "private_network", ip: "192.168.56.#{10+i}" 
 
      # 设置主机与虚拟机的共享目录 
      #node.vm.synced_folder "/Users/meetmax", "/home/vagrant/code" 
      node.vm.synced_folder "E:/volum/data", "/share", create:true, owner: "root", group: "root"
      # VirtaulBox相关配置 
      node.vm.provider "virtualbox" do |v| 
 
          # 设置虚拟机的名称 
          v.name = "node#{i}" 
 
          # 设置虚拟机的内存大小 
          v.memory = 2048 
 
          # 设置虚拟机的CPU个数 
          v.cpus = 2 
      end 
  end 
end 
end 
```
执行 `vagrant up` 启动node1,node2,node3 三台虚拟机，在启动过程中可能会报挂载失败的错误：

```
node1: /share => E:/vagrant/data
Vagrant was unable to mount VirtualBox shared folders. This is usually
because the filesystem "vboxsf" is not available. This filesystem is
made available via the VirtualBox Guest Additions and kernel module.
Please verify that these guest additions are properly installed in the
guest. This is not a bug in Vagrant and is usually caused by a faulty
Vagrant box. For context, the command attempted was:

mount -t vboxsf -o uid=0,gid=0,_netdev share /share
```

安装插件可以解决（如果还报错则卸载插件，换一个更低版本的）：

```
## 安装插件
vagrant plugin install vagrant-vbguest

## 卸载插件
vagrant plugin uninstall vagrant-vbguest

## 安装指定版本插件
vagrant plugin install vagrant-vbguest --plugin-version 0.21

## 插件安装过程中可能出现联网失败的情况，可以修改下载国内镜像安装
vagrant plugin install vagrant-vbguest --plugin-clean-sources --plugin-source https://gems.ruby-china.com/
```


执行 `vagrant status` 查看虚拟机运行状态，也可以直接在virtualbox 界面上查看，在下次启动虚拟机的时候就不需要再cmd窗口执行`vagrant up指令`，直接在virtualbox界面上选择启动方式。
进入虚拟机的指令：
```
vagrant ssh '虚拟机名称'
```

## 虚拟机初始化配置
在我们虚拟机启动后，其中dns 服务器地址是有问题的，我们希望在创建虚拟机的时候，进行一些基础的配置，我们可以在Vagrantfile 中添加脚本实现这些配置：
```
Vagrant.configure("2") do |config| 
    (1..3).each do |i| 
       
        #定义节点变量 
        config.vm.define "node#{i}" do |node| 
      
        # box配置 
        node.vm.box = "centos7" 
 
        # 设置虚拟机的主机名 
        node.vm.hostname = "node#{i}" 
 
        # 设置虚拟机的IP 
        node.vm.network "private_network", ip: "192.168.56.#{10+i}" 
 
        # 设置主机与虚拟机的共享目录 
        #node.vm.synced_folder "E:\volum\data", "/share", create:true, owner: "root", group: "root"
        # VirtaulBox相关配置 
        node.vm.provider "virtualbox" do |v| 
 
          # 设置虚拟机的名称 
          v.name = "node#{i}" 
 
          # 设置虚拟机的内存大小 
          v.memory = 2048 
 
          # 设置虚拟机的CPU个数 
          v.cpus = 2 
        end
        node.vm.provision "shell", inline: <<-SHELL
            eh=$(nmcli connection show|awk -F "  " '{print $1}'|grep -v NAME|head -1)
            nmcli con mod "$eh" ipv4.dns "223.5.5.5"
            nmcli con up "$eh"
        SHELL
        end 
    end 
end 
```
在示例中，我通过配置项`node.vm.provision "shell"` 编写脚本设置了dns 服务器ip。通过这个方式我们可以实现预装docker等操作。
我们还可以指定外部脚本，配置示例：
```
config.vm.provision "shell", path: "script.sh"
```

当然我们还可以制作自己的box 实现一键创建虚拟机。
首先我们在原来的虚拟机中安装好软件并修改相关配置配置文件，然后清除掉private_network的网络规则：

```
sudo rm -f /etc/udev/rule.d/70-persistent-net.rules
```
检查 /etc/ssh/sshd_config文件PasswordAuthentication no 是否被注释掉，没有注释掉的话无法通过 `vagrant ssh` 登录。

然后执行指令：
```
vagrant package node1 
```
在当前文件夹下得到一个 package.box 。

vagrant package 极大的增强了虚拟机的可移植性，在一定程度上降低了我们的学习成本。

### 备注

vagrant 常用指令速查表：

```
# 查看box 
vagrant box list

# 添加box
vagrant box add --name path

# 移除box
vagrant box remove xxx

# 初始化 Vagrantfile
vagrant init xxx

# 启动虚拟机
vagrant up [name]

# ssh 连接虚拟机
vagrant ssh

# 重新加载虚拟机
vagrant reload

# 删除虚拟机
vagrant destroy

# 打包虚拟机为镜像
vagrant package 

# 查看虚拟机状态
vagrant status
```


```
  nmcli connection show
  
  nmcli con mod "System eth0" ipv4.dns "223.5.5.5 223.6.6.6"
  
  sudo nmcli con up "System eth0"

  sudo yum install wget
  sudo yum update -y
  sudo yum install docker -y
   sudo mv /etc/yum.repos.d/CentOS-Base.repo CentOS-Base.repo.back
   sudo wget -O /etc/yum.repos.d/CentOS-Base.repo https://mirrors.aliyun.com/repo/Centos-7.repo

   yum clean all

yum makecache

sudo rm -f /etc/udev/rule.d/70-persistent-net.rules
sudo systemctl enable docker
sudo vi /etc/docker/daemon.json
 "registry-mirrors": ["https://b9pmyelo.mirror.aliyuncs.com"]

```

https://blog.csdn.net/lovoo/article/details/119175523?ops_request_misc=%257B%2522request%255Fid%2522%253A%252218CB3CEA-B719-4292-A6BA-8A71F6DF649E%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fblog.%2522%257D&request_id=18CB3CEA-B719-4292-A6BA-8A71F6DF649E&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~blog~first_rank_ecpm_v1~rank_v31_ecpm-3-119175523-null-null.nonecase&utm_term=%E4%BD%BF%E7%94%A8vagrant%E6%90%AD%E5%BB%BAk8s&spm=1018.2226.3001.4450

https://blog.csdn.net/lovoo/article/details/119180165?ops_request_misc=%257B%2522request%255Fid%2522%253A%252218CB3CEA-B719-4292-A6BA-8A71F6DF649E%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fblog.%2522%257D&request_id=18CB3CEA-B719-4292-A6BA-8A71F6DF649E&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~blog~first_rank_ecpm_v1~rank_v31_ecpm-5-119180165-null-null.nonecase&utm_term=%E4%BD%BF%E7%94%A8vagrant%E6%90%AD%E5%BB%BAk8s&spm=1018.2226.3001.4450