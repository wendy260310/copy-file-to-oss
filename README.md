## 1. 说明
这个代码是[intellij idea](https://www.jetbrains.com/idea/)的一个插件，用来把项目里面的某些文件拷贝到[oss](https://oss.console.aliyun.com/overview)服务器上。

## 2.使用说明
### 2.1 安装osscmd 
请按照[官方说明](https://help.aliyun.com/document_detail/32184.html) 安装osscmd，配置[osscmd](https://help.aliyun.com/document_detail/32185.html?spm=a2c4g.11186623.6.1280.6bd9135eyVFSWn)

### 2.2 配置插件
在intellij 下，右键project，找到Copy to Oss，弹窗选择config bulk。
* bulk 表示你想上传到oss上的bulk名字
* osscmd path 表示这个osscmd的安装路径，mac上得指定这个文件安装路径，不然会报错。
* file prefix 表示上传到oss后文件前缀，目前上传到oss上文件形式是：${file prefix}/${file name}。例如上传的文件是 controller包下的AbcController,file prefix设置的为：abc,文件上传到oss的路径是：abc/AbcController

### 2.3 使用
在intellij 下，右键project，找到Copy to Oss，弹窗选择copy to oss,弹窗会提示是否上传成功～
