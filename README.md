<img src="http://images.cnblogs.com/cnblogs_com/liezhengli/923235/t_48.png" width="48px" height="48px"/><h2 style="display:inline;">put-me-down</h2>
--------
### 目录
--------
* [简介](#1)
* [相关信息](#2)
* [代码规范](#3)
* [使用方法](#4)
* [更新日志](#5)

<h4 id="1">简介</h4>
PMD —— 一款帮助减少手机使用时间的APP，该APP面向所有低头族，主要面向大学生。
该APP的目的是以一种"温和"的方式帮助减少使用手机的时间.

<h4 id="2">相关信息</h4>
* 开发环境  
* 系统 : windows 10
* IDE : Android Studio ,Eclipse
* App运行环境 : Android 5.0 以上
* 开发语言 : java
 
 <h4 id="3">编码规范</h4>
文件编码：UTF-8
*包名包名全部小写，连续的单词只是简单地连接起来，不使用下划线

包名|备注
-----|-----
com.xx.应用名称缩写.activity | 页面用到的Activity类 
com.xx.应用名称缩写.adapter | 页面用到的Adapter类
com.xx.应用名称缩写.util | 公共工具方法类 
com.xx.应用名称缩写.model | 模型类 
com.xx.应用名称缩写.view | 自定义的View类 
com.xx.应用名称缩写.db | 数据库操作类 
com.xx.应用名称缩写.service | Service服务 
com.xx.应用名称缩写.receiver | BroadcastReceiver服务

* 类名  
类名都以UpperCamelCase风格编写   
 
类名|描述|例
----|----|----
dapter类	|Adapter 为后缀标识	|新闻详情适配器 NewDetailAdapter
解析类	|Parser为后缀标识|首页解析类HomePosterParser
工具方法类	|Util或Manager为后缀标识	|日志工具类：LogUtil
数据库类	|以DBHelper后缀标识	|新闻数据库：NewDBHelper
Service类	|以Service为后缀标识	|时间服务TimeService
Receiver类	|以Receiver为后缀标识 |推送接收JPushReceiver
自定义的共享基础类|	以Base开头	|BaseActivity,BaseFragment

* 方法名   
方法名都以 LowerCamelCase 风格编写

* 常量名   
全部字母大写，用下划线分隔单词

* 变量名   
变量名都以 LowerCamelCase 风格编写

 * 资源文件名  
全部小写，采用下划线命名法

*  所有Activity或Fragment的contentView必须与其类名对应，对应规则为：将所有字母都转为小写，将类型和功能调换（也就是后缀变前缀）。
*  列表项命名：item_描述.xml  
例如：item_city.xml
*  包含项命名：模块_(位置)描述.xml
*  layout中的id命名，如：username_tv
*  资源文件，加前缀区分，如果有多种形态如按钮等除外如 btn_xx.xml

 

<h4 id="4">使用方法</h4>
>

<h4 id="5">更新日志</h4>
> 
* 2016/12/10 21:35  
相关信息的编写
* 2016/12/9 10:20    
README的整体框架和编码规范 
