<template>
    <div class="left_menu" v-if="getEmailAccountDeviceCheck">
        <ul>
            <router-link to="/SetProfile" tag="li" exact-active-class="on">프로필 수정</router-link>
            <router-link to="/SetAccount" tag="li" exact-active-class="on">계정관리</router-link>
            <router-link to="/SetAliam" tag="li" exact-active-class="on">알림</router-link>
            <router-link to="/SetBusiness" tag="li" exact-active-class="on">비즈니스계정전환</router-link>
            <router-link to="/SetBlock" tag="li" exact-active-class="on">차단 사용자 관리</router-link>
            <router-link to="/SetNotice" tag="li" exact-active-class="on">공지사항</router-link>
            <router-link to="/SetHelp" tag="li" exact-active-class="on">도움말</router-link>
            <router-link to="/SetInquiry" tag="li" exact-active-class="on">문의 및 제안하기</router-link>
            <router-link to="/SetList" tag="li" exact-active-class="on">활동이력</router-link>
            <router-link to="/Logout" tag="li">로그아웃</router-link>
        </ul>
    </div>
</template>

<script>
    import {mapGetters} from 'vuex'
    export default {
        name: "SideMenu",
        computed : {
            ...mapGetters([
                'getEmailAccountDeviceCheck'
            ])
        },
        data : function () {
            return {

            }
        }
    }
</script>

<style scoped>

</style>
