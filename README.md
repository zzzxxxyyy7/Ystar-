## Ystar直播

### 项目描述：Netty搭建直播平台，功能包括消息互发、直播打赏、主播PK等，自研IM通信服务器和分布式唯一ID生成器，开发高性能用户中台，支持亿级数据存取，根据压力模型拆解服务边界，引入MQ解耦削峰。

### 技术栈：SpringCloud、Netty、Dubbo、RabbitMQ、Redis、MySQL
- 自研IM服务器，基于推拉模型实现在线/离线均可获取直播间全程消息，引入重试机制确保消息送达
- 基于数据库号段模式自研分布式唯一ID生成器，供给订单、送礼、注册服务高效获取唯一ID
- 扩展Dubbo SPI机制，实现消息向特定用户所在IM转发，避免消息风暴，提高IM的可靠性
- 使用MQ、线程池等技术，多处采用多线程或消息投递方式执行，提高了约50%的执行速度
- 搭建MySQL主从集群，延迟双删解决主从一致问题，保障DB的高度可用
- 针对多处先查缓存再改数据如余额，PK进度条等，通过分布式锁和Lua脚本保证俩个操作的原子性
- 单机环境下，4核8G服务器Jmeter每1500次并发请求平均响应时间在14ms，错误率在0.81%，吞吐量在1728/s左右
