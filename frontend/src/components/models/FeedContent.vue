<template>
    <p v-if="parseDataList.length">
        <template v-for="tag in parseDataList">
            <a class="TagLink" v-if="tag.type == 'hashTag'" @click="findTagByIdx(tag.tagIndex)">{{tag.txt}}</a>
            <a class="TagLink" v-else-if="tag.type == 'userTag'" @click="findMemberByIdx(tag.userIndex)">{{tag.txt}}</a>
            <template v-else>{{tag.txt}}</template>
        </template>
        <a class="more_txt" v-on:click="moveDetail()" v-if="!detail">더 보기</a>
    </p>
</template>

<script>
    export default {
        name: 'FeedContent',
        props: ['idx', 'contents', 'tags', 'feedMemberTag', 'detail'],
        data: function () {
            return {
                parseDataList: [],
            }
        },
        methods: {
            findTagByIdx: function (idx) {
                this.$router.push({'name': 'TagFeed', params: {tagIdx: idx}});
            },
            findMemberByIdx: function (idx) {
                this.$router.push({'name': 'MemberFeed', params: {memberIdx: idx}});
            },
            moveDetail: function () {
                this.$router.push({'name': 'FeedView', params: {feedIdx: this.idx}});
            },
            refresh: function () {
                this.parseDataList = [];
                if (this.contents) {
                    this.parseData();
                }
            },
            parseData: function () {
                let regex = /(#[^\s#]+)|(@[^ @]+)|(ꊞ[0-9]+)/gm;
                let content_split = this.contents.split(regex);
                for (i in content_split) {
                    if (typeof content_split[i] !== 'undefined') {
                        if (content_split[i].startsWith('ꊞ')) {
                            let findMemberIdx = content_split[i].substr(1);

                            if (typeof this.feedMemberTag !== 'undefined') {
                                let member = this.feedMemberTag.filter(member => member.idx == findMemberIdx);
                                if (member.length) {
                                    this.parseDataList.push({
                                        type: 'userTag',
                                        txt: '@' + member[0].nickName,
                                        userIndex: findMemberIdx
                                    });
                                }
                            }
                        } else if (content_split[i].startsWith('#')) {
                            let findTag = content_split[i].substr(1);

                            if (typeof this.tags !== 'undefined') {
                                let tag = this.tags.filter(tag => tag.tag == findTag);
                                if (tag.length) {
                                    this.parseDataList.push({
                                        type: 'hashTag',
                                        txt: content_split[i],
                                        tagIndex: tag[0].idx
                                    });
                                }
                            }
                        } else {
                            this.parseDataList.push({
                                type: 'text',
                                txt: content_split[i]
                            });
                        }
                    }
                }
            }
        },
        created: function () {
            this.refresh();
        }, watch: {
            contents: function () {
                this.refresh();
            }
        }
    }
</script>

<style scoped>

</style>
