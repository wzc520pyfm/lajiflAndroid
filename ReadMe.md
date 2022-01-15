### 1. 登录百度智能云并实名认证([百度智能云-智能时代基础设施 (baidu.com)](https://cloud.baidu.com/?from=console))

### 2. 管理控制台 -> 产品服务 -> 图像识别 -> 创建应用 -> (免费领取接口调用次数) -> 选择应用归属为个人(并填好所有必填项) -> 创建应用

### 3. 查看应用详情, 获取到 API Key 和  Secret Key

### 4. 利用第三步获取到的key请求 '百度鉴权access_token接口' 获取access_token(有效期为30天) ([通用参考 - 鉴权认证机制 | 百度AI开放平台 (baidu.com)](https://ai.baidu.com/ai-doc/REFERENCE/Ck3dwjhhu))

### 5. 获取access_token后请求百度图像识别接口, access_token放在params参数里, 图片的base64编码放在body(使用from传参)里 ([图像识别 - 通用物体和场景识别 | 百度AI开放平台 (baidu.com)](https://ai.baidu.com/ai-doc/IMAGERECOGNITION/Xk3bcxe21))

### 6. 注册并登录木小果平台, 个人中心页->我的接口 -> 购买垃圾分类接口(0元) -> 获取到api_key (https://api.muxiaoguo.cn/)

### 7. 将第五步识别到的物体信息作为第6步中垃圾分类的参数值, 请求 垃圾分类接口 获取返回值 ([垃圾分类 - 木小果API (muxiaoguo.cn)](https://api.muxiaoguo.cn/doc/lajifl.html))





### 项目结构

1. `grant_type`    `client_id`    `client_secret`   `api_key `  四个关键参数均存放在values/string.xml下
2. 项目使用到: 
   * butterknife注入
   * andoridUI组件库
     * dialog (https://github.com/yarolegovich/LovelyDialog)
     * 加载动画 (https://github.com/HarlonWang/AVLoadingIndicatorView)
   * retrofit网络请求库
   * gson
3. 项目所使用的用户手机权限
   * 文件读写权限
   * 相机权限
   * 网络请求权限
4. 目录结构
   * api : retrofit接口
   * bean :  实体类, 解析网络请求返回的数据
   * http :  网络请求
   * task :  线程
   * utils :  工具类
5. 阿里巴巴矢量图标库

