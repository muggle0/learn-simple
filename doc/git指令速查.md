
### 基本配置

```
### 初始化git 仓库
git init

### 设置git 全局变量
git config --global user.name"ssss"

### 将文件纳入 git 管理
git add README.md

### 提交
git commit -m "first commit"

### git 添加远程仓库
git remote add origin https://github.com/xxx

###  将本地的master分支推送到origin主机，同时指定origin为默认主机，后面就可以不加任何参数使用git push了
git push -u origin master

```

### 查看

```
### 查看分支
git checkout master

###
git checkout commitid

```

### 覆盖/撤回

```
### 对受保护分支无法使用强推，强推解决多仓库不同源问题
git push 远程仓库名 本地分支:远程仓库名 --force

### git revert是用一次新的commit来回滚之前的commit，git reset是直接删除指定的commit 。
### git reset --hard --mixed(default) --soft 分别覆盖3个、2个、1个位置的代码，--mixed（默认的参数）只会保留working copy里的代码

git reset --hard HEAD^

```

HEAD的含义：代表当前仓库最新版本。`HEAD^` 和`HEAD~`的意义和区别HEAD^+数字表示当前提交的父提交。具体是第几个父提交共同过^+数字指定，EAD^1第一个父提交，该语法只能用于合并(merge)的提交记录，因为一个通过合并产生的commit对象才有多个父提交。`HEAD~`(等同于HEAD^,注意没有加数字)表当前提交的上一个提交。<br>如果想获取一个提交的第几个父提交使用HEAD^+数字,想获取一个提交的上几个提交使用HEAD~。HEAD^和HEAD~或HEAD^^和HEAD~~并没有区别，只有HEAD^+数字才和HEAD~有区别。 git reset 撤销方式git reset --soft 版本号只撤销本地仓库数据到版本号git reset --mixed 版本号该方式为默认方式（即git reset 版本号）撤销本地和暂存区仓库到版本号git reset --hard 版本号撤销 工作区 暂存区 本地仓库到版本号git reset --hard origin/master远程仓库代码覆盖工作区 暂存区 本地仓库以上指令都不会对未归入git控制的文件进行管理也就是从未add过的文件git是不会去删除撤销它的撤销单个文件的修改git reset HEAD xxx.txt本地覆盖暂存区的代码git checkout xxx.txtgit checkout .将暂存区的代码覆盖工作区 “.”是通配所有文件