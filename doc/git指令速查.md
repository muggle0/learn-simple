
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

### 查看commit
git checkout commitid

### 切换远程分支
git checkout -b dev origin/dev   

### 查看本地分支关联的远程分支
git branch -vv 

```

### 覆盖/撤回

```
### 对受保护分支无法使用强推，强推解决多仓库不同源问题
git push 远程仓库名 本地分支:远程仓库名 --force

### git revert是用一次新的commit来回滚之前的commit，git reset是直接删除指定的commit 。
### git reset --hard --mixed(default) --soft 分别覆盖3个、2个、1个位置的代码，--mixed（默认的参数）只会保留working copy里的代码

git reset --hard HEAD^

### 强制push ,会覆盖其他提交记录 慎用
git push -f 

### 强制pull

git pull origin master --allow-unrelated-histories 

```

### 其他

```
### 多仓库远程推送
git push origin local:remote

### 对于提交错误但未push的commit 可以使用idea 的undo commit 进行回滚

### 合并
git merge dev 

### 删除本地的远程仓库地址
git remote rm origin 

### 删除远程分支
git push origin --delete dev 

### 选择合并（摘樱桃）
git cherry-pick <commit_id>
```

### git subtree

git subtree 是在当前仓库下创建子目录，适用于多仓库间公共代码的维护

```
### 添加子仓库
git subtree add --prefix LibraryC libraryc master 

### 拉取子仓库代码

git subtree pull --prefix LibraryC libraryc master

### 提交子仓库代码
git subtree push --prefix LibraryC libraryc master 
```

HEAD^ 和HEAD~的意义和区别：
- `HEAD^`+数字表示当前提交的父提交。具体是第几个父提交通过^+数字指定，`HEAD^1`第一个父提交，该语法只能用于合并(merge)的提交记录，因为一个通过合并产生的commit对象才有多个父提交。

- `HEAD~`(等同于`HEAD^`,注意没有加数字)表当前提交的上一个提交。 如果想获取一个提交的第几个父提交使用`HEAD^`+数字,想获取一个提交的上几个提交使用`HEAD~``。

- `HEAD^`和`HEAD~`或`HEAD^^`和`HEAD~~`并没有区别，只有`HEAD^`+数字才和`HEAD~`有区别。