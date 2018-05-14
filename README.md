电影《后来的我们》影评分析

过程:
1,对豆瓣上的所有相关数据进行爬取并使用jsoup解析出影评数据
2,使用IK分词器对影评数据进行智能分词
3,使用hadoop分析出用户最关心的前20个词组(统计,排序)
4,使用word art词云展示结果

分析结果如下(图片在data文件夹中):
![image](https://github.com/littleteaknow/weLater/blob/master/data/%E5%90%8E%E6%9D%A5%E7%9A%84%E6%88%91%E4%BB%AC%E5%BD%B1%E8%AF%841.png)
![image](https://github.com/littleteaknow/weLater/blob/master/data/%E5%90%8E%E6%9D%A5%E7%9A%84%E6%88%91%E4%BB%AC%E5%BD%B1%E8%AF%842.png)


另外src中的tushu包是爬虫的参考代码,和本项目无关
