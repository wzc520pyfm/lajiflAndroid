#### 运行效果

<img src="https://cdn.jsdelivr.net/gh/wzc520pyfm/Picbed_PicGo@master/img/Screenshot_20220404_225720_com.baidu.duer.lajifl.jpg" alt="Screenshot_20220404_225720_com.baidu.duer.lajifl" style="width:20%;height:20%;" /><img src="https://cdn.jsdelivr.net/gh/wzc520pyfm/Picbed_PicGo@master/img/Screenshot_20220404_225724_com.baidu.duer.lajifl.jpg" alt="Screenshot_20220404_225724_com.baidu.duer.lajifl" style="width:20%;height:20%;" />

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
   * butterknife注入---简化代码,避免犯错,省去了繁琐的findView
     * 示例博客 [Android常用之Butterknife使用详解 - SegmentFault 思否](https://segmentfault.com/a/1190000016460847)
   * andoridUI组件库---快速开发必备
     * dialog (https://github.com/yarolegovich/LovelyDialog)
     * 加载动画 (https://github.com/HarlonWang/AVLoadingIndicatorView)
   * retrofit网络请求库---封装网络请求,高度解耦
     * 官方文档 [改造 (square.github.io)](https://square.github.io/retrofit/)
     * 示例博客 [(37条消息) Retrofit2 详解和使用（一）_花酒果子-CSDN博客_retrofit2](https://blog.csdn.net/m0_37796683/article/details/90702095)
   * gson
     * retrofit使用的的json解析器, 用于解析网络请求的响应数据
   
3. 项目所使用的用户手机权限(在app/manifests/AndroidManifest.xml中配置)
   * 文件读写权限
   * 相机权限
   * 网络请求权限
   
4. 目录结构
   * api : retrofit接口
   * bean :  实体类, 规定如何解析网络请求返回的数据
   * http :  网络请求, 实现api中的接口并将收到的网络响应数据解析为bean实体类
   * task :  线程, 执行网络请求
     * 网络请求等耗时任务必须新开一个线程执行, 不能在主线程中进行网络请求
   * utils :  工具类
     * ImageUtil :  图片处理工具类
     * SharedUtil :  全局状态存取工具类, 用于保存较小的数据
   
5. 安卓项目目录介绍

   * manifests

     * 项目配置文件, 包括页面配置, 权限配置, 应用图标等

   * java

     * 逻辑代码
     * Test
       * 测试类, 用于编写测试用例

   * res

     资源文件

     * drawable

       * 文件资源, 多为xml, png, jpg, svg等文件

     * layout

       * 页面布局文件

     * mipmap

       * 适配image, 提供了不同尺寸的image资源文件以实现对不同尺寸屏幕的适配, 应用图标一定是存放在这里, 这里也可以存放其他的需要支持自适应的图片

     * values

       存放常量

       * colors.xml
         * 颜色常量
       * strings.xml
         * 字符串常量
       * styles.xml
         * 主题

   * Gradle

     安卓构建工具.  项目依赖于各种程序包, 必须将项目所需的程序包都下载到项目, 项目才能编译运行, 但这些程序包本身不应该成为项目的关键代码, 始终将这些程序包保留在项目中会造成冗余, 为此, gradle将项目所需的依赖包记录下来, 在需要的时候再将其下载, 减小项目体积. 包括项目的编译, 打包等均由gradle完成

     * build.gradle
       * build.gradle(Project)
         * 项目层面的gradle配置, 里面配置了依赖包源, gradle版本, 及一些特殊的gradle命令
       * build.gradle(Module)
         * 应用层面的gradle配置, 这里注意配置JDK版本, 应用版本, 应用信息, 打包配置, 编译配置, 项目依赖包配置等

6. 阿里巴巴矢量图标库

   * icon图标

