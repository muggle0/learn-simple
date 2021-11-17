```java

function githubClone (url) {
  if (!(/^(https:\/\/github\.com\/[\w-]+\/([\w.-]+))(\/tree\/(?!main))?/ .test(url))) {
    throw new Error('URL 匹配错误')
  }
  let cloneBash = ''
  let gitUrl = RegExp.$1
  if (!gitUrl.endsWith('.git')) gitUrl += '.git'
  let projectName = RegExp.$2
  // 根据窗口标题 判断项目是否在分支上
  if (Boolean(RegExp.$3) && / at ([\w.\-/]+)/.test(ENTER.payload.title)) {
    const branch = RegExp.$1
    projectName += '@' + branch
    //  clone 分支
    cloneBash = `git clone --single-branch --branch ${branch} ${gitUrl} ${projectName}`
  } else {
    cloneBash = `git clone ${gitUrl}`
  }
  // 把 clone 位置放置在 下载目录
  const downloadPath = utools.getPath('downloads')
  const projectPath = require('path').join(downloadPath, projectName)
  if (utools.isMacOs()) {
    // 运行 AppleScript  激活终端 并执行脚本
    runAppleScript(`tell application "Terminal"
      activate
      do script "cd ${downloadPath} && ${cloneBash} && open ${projectPath}"
    end tell`)
    return
  } 
  if (utools.isWindows()) {
    // 打开CMD 并执行脚本
    require('child_process').spawn('start', ['cmd', '/k', `"cd /d ${downloadPath} && ${cloneBash} && start ${projectPath} && exit" `], { shell: 'cmd.exe', detached: true })
  }
}

if (ENTER.type === 'regex') {
  return githubClone(ENTER.payload)
}

utools.readCurrentBrowserUrl().then(githubClone)


```