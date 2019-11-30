package com.example.zhihuuiapplication.bean;

import java.io.Serializable;
import java.util.List;

public class Bean implements Serializable {


    /**
     * date : 20191114
     * stories : [{"image_hue":"0xf7d11a","title":"锂电池的下一代会是什么电池？","url":"https://daily.zhihu.com/story/9717148","hint":"弗雷刘 · 3 分钟阅读","ga_prefix":"111416","images":["https://pic1.zhimg.com/v2-d4a309a586febbe9d4e8f9de84020758.jpg"],"type":0,"id":9717148},{"image_hue":"0xc9c9e6","title":"《英雄联盟》还能火几年？最可能被哪个游戏取代？","url":"https://daily.zhihu.com/story/9717097","hint":"Kayle · 6 分钟阅读","ga_prefix":"111409","images":["https://pic3.zhimg.com/v2-77b7dfc49767f99b13d41c45be6263ba.jpg"],"type":0,"id":9717097},{"image_hue":"0x7e8380","title":"如何理解多巴胺的具体作用？","url":"https://daily.zhihu.com/story/9717145","hint":"赵思家 · 4 分钟阅读","ga_prefix":"111407","images":["https://pic1.zhimg.com/v2-900a848c47c47c5852c4cea9c0a30660.jpg"],"type":0,"id":9717145},{"image_hue":"0x599756","title":"瞎扯 · 如何正确地吐槽","url":"https://daily.zhihu.com/story/9717385","hint":"VOL.2261","ga_prefix":"111406","images":["https://pic1.zhimg.com/v2-059d7ee29a3739f29d901618ef86b89c.jpg"],"type":0,"id":9717385}]
     * top_stories : [{"image_hue":"0x838d84","hint":"作者 / Ellen","url":"https://daily.zhihu.com/story/9716798","image":"https://pic2.zhimg.com/v2-ff7f892ad30add54de1baa35568eb65d.jpg","title":"为什么有的人喜欢半英文半中文讲话？","ga_prefix":"110607","type":0,"id":9716798},{"image_hue":"0x97a4ae","hint":"作者 / 陈大可","url":"https://daily.zhihu.com/story/9716567","image":"https://pic3.zhimg.com/v2-3458c80a7eaa1f47f3869e30848f0dc6.jpg","title":"如何看待非全日制研究生受到的就业歧视？","ga_prefix":"110320","type":0,"id":9716567},{"image_hue":"0xe0ddc6","hint":"作者 / Jianfeng","url":"https://daily.zhihu.com/story/9716793","image":"https://pic1.zhimg.com/v2-db3d89c7c4b8025d25895d293f90c7fc.jpg","title":"未来 20 年，中国能否成为向世界提供平价药品的主要国家？","ga_prefix":"110307","type":0,"id":9716793},{"image_hue":"0xe1e1e1","hint":"作者 / Cecilia","url":"https://daily.zhihu.com/story/9717009","image":"https://pic3.zhimg.com/v2-b31c56ec6f512cf19f0246a4d1a371d6.jpg","title":"「30 岁，买个 AirPods Pro 要纠结好久」","ga_prefix":"110116","type":0,"id":9717009},{"image_hue":"0x9c9b42","hint":"作者 / Mr-HH","url":"https://daily.zhihu.com/story/9716617","image":"https://pic4.zhimg.com/v2-b1663d2b817b9c228d171c0db7b5f5fb.jpg","title":"除了视觉，动物界还有哪些用来感知周围世界的奇妙能力？","ga_prefix":"103109","type":0,"id":9716617}]
     */

    private String date;
    private List<StoriesBean> stories;
    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public Bean(String date, List<StoriesBean> stories, List<TopStoriesBean> top_stories) {
        this.date = date;
        this.stories = stories;
        this.top_stories = top_stories;
    }

    public static class StoriesBean {
        /**
         * image_hue : 0xf7d11a
         * title : 锂电池的下一代会是什么电池？
         * url : https://daily.zhihu.com/story/9717148
         * hint : 弗雷刘 · 3 分钟阅读
         * ga_prefix : 111416
         * images : ["https://pic1.zhimg.com/v2-d4a309a586febbe9d4e8f9de84020758.jpg"]
         * type : 0
         * id : 9717148
         */

        private String image_hue;
        private String title;
        private String url;
        private String hint;
        private String ga_prefix;
        private int type;
        private int id;
        private List<String> images;

        public String getImage_hue() {
            return image_hue;
        }

        public void setImage_hue(String image_hue) {
            this.image_hue = image_hue;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public StoriesBean(String image_hue, String title, String url, String hint, String ga_prefix, int type, int id, List<String> images) {
            this.image_hue = image_hue;
            this.title = title;
            this.url = url;
            this.hint = hint;
            this.ga_prefix = ga_prefix;
            this.type = type;
            this.id = id;
            this.images = images;
        }
    }

    public static class TopStoriesBean {
        /**
         * image_hue : 0x838d84
         * hint : 作者 / Ellen
         * url : https://daily.zhihu.com/story/9716798
         * image : https://pic2.zhimg.com/v2-ff7f892ad30add54de1baa35568eb65d.jpg
         * title : 为什么有的人喜欢半英文半中文讲话？
         * ga_prefix : 110607
         * type : 0
         * id : 9716798
         */

        private String image_hue;
        private String hint;
        private String url;
        private String image;
        private String title;
        private String ga_prefix;
        private int type;
        private int id;

        public String getImage_hue() {
            return image_hue;
        }

        public void setImage_hue(String image_hue) {
            this.image_hue = image_hue;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public TopStoriesBean(String image_hue, String hint, String url, String image, String title, String ga_prefix, int type, int id) {
            this.image_hue = image_hue;
            this.hint = hint;
            this.url = url;
            this.image = image;
            this.title = title;
            this.ga_prefix = ga_prefix;
            this.type = type;
            this.id = id;
        }
    }
}
