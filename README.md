# 淘宝联盟获取佣金、优惠券
有什么想说的吗？ 没有。

## 开源原因
由于技术原因无法解决淘宝cookie过期问题。

## 免责声明
此项目仅作为学习使用，无商用打算。

## 技术涵盖
> * headless chrome 获取cookie以及短链接解析
> * selenium 单进程多tab操作chrome 无需多次new与close操作
> * httpClient 4.x get与post请求方法极限封装
> * spring boot 请求拦截器 Interceptor
> * request Header 线程安全单例模式 存储cookie

## 技术预演
> * @ControllerAdvice 注解全局统一处理异常
> * @Async() 异步线程池的改进与使用
> * Callable 实现并发编程 (线程池无法自定义)
> * DeferedResult 实现异步处理 (可自主干预线程池)

## 框架构思
> 主要构思: java bean 随遇而安 不揉成一团堆在一个文件夹下
> * 没有service层 因为所以 懒得解耦controller
> * common 包含配置类、全局枚举类、工具类
> * api 包含controller与view(服务端返回给前端的数据格式)
> * job 所有定时任务类
> * advice 封装所有http请求 包含请求与解析的java bean
> * 没有core层 包含mapper与entity类 (服务都跑不通 还考虑数据库么)

## 开源协议
Copyright 2018 valord577

Licensed under the Apache License, Version 2.0 (the "License"); you may
not use this file except in compliance with the License. You may obtain
a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations
under the License.
