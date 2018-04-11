# springboot-mongodb
SpringBoot2.0+MongoDB3.6.3
用Spring Boot + MongoDB，做了一个作品三级评论的功能，本项目未编写页面客户端脚本。用户可以发表评论；其他用户可以对作品现有的评论进行回复；最顶级的评论者，可以对回复评论的人再回复评论。其中，评论的数据结构如下：
    t_resource_comment {  
        _id:,  
        resource_id: 123,  
        comment_user_id: "A",  
        comment_user_name:,  
        comment_content: "A的评论",  
        ctime:,  
        status:,  
        comment_responses: [  
            {  
                response_user_id: "B",  
                response_user_name:,  
                response_content:[  
                    {ctime:, content:"B给A的第一个评论"},  
                    {ctime:, content:"B给A的第二个评论"},  
                    {ctime:, content:"B给A的第三个评论"}  
                ]  
                get_reply:[  
                    {ctime:, content:"这是A给B的某一个评论的回复如果有就对应插入index对应的元素没有就是空串"},  
                    {ctime:, content:"A没有回复B这一条就是空串"},  
                    {ctime:, content:"A个神经病跳着回复了这一条评论，这数组的第三个元素就是A回复的内容"}  
                ]  
            },  
            {  
                response_user_id: "C",  
                response_user_name:,  
                response_content:[  
                    {ctime:, content:"C给A的第一个评论"},  
                    {ctime:, content:"C给A的第二个评论"},  
                    {ctime:, content:"C给A的第三个评论"}  
                ]  
                get_reply:[  
                    {ctime:, content:"这是A给C的某一个评论的回复如果有就对应插入index对应的元素没有就是空串"},  
                    {ctime:, content:"A没有回复C这一条就是空串"},  
                    {ctime:, content:"A个神经病跳着回复了这一条评论，这数组的第三个元素就是A回复的内容"}  
                ]  
            },  
            ...  
        ]  
    }  
    
    比如，A对文章或作品123发表了一个评论；B看到发表的评论后，给A的评论回复了一个评论；A看到B的回复后，又对B的回复评论做了一个回复。一共三级。
    
    
