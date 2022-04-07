xpose模块功能 

1. 可以将sink文件看作metadata，来hook具体的方法 
2. 通过观察者+装饰器模式，实时监控metadata的变化，一旦变化将之前所有方法unhook，然后hook新的方法（目前先做这么粗粒度的先） 
3. 一次性hook400来个方法，基本没有性能问题