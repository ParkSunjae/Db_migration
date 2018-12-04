import Vue from 'vue'
import Router from 'vue-router'

// 회원
import Join from '@/components/join/Join'
import PasswordSend from '@/components/join/PasswordSend'
import StepOne from '@/components/join/StepOne'
import StepTwo from '@/components/join/StepTwo'
import StepThree from '@/components/join/StepThree'


import Login from '@/components/Login'
import Logout from '@/components/Logout'

import MainFeed from '@/components/feed/MainFeed'
import TagFeed from '@/components/feed/TagFeed'
import MemberFeed from '@/components/feed/MemberFeed'
import Write from '@/components/feed/Write'
import Modify from '@/components/feed/Modify'
import FeedView from '@/components/feed/FeedView'
import SearchFeed from '@/components/feed/SearchFeed'


import Intro from '@/components/Intro'

import MyFeed from '@/components/myFeed/MyFeed'
import SetAccount from '@/components/userSettings/SetAccount'
import SetAliam from '@/components/userSettings/SetAliam'
import SetBlock from '@/components/userSettings/SetBlock'
import SetBusiness from '@/components/userSettings/SetBusiness'
import SetHelp from '@/components/userSettings/SetHelp'
import SetInquiry from '@/components/userSettings/SetInquiry'
import SetList from '@/components/userSettings/SetList'
import SetNotice from '@/components/userSettings/SetNotice'
import SetProfile from '@/components/userSettings/SetProfile'
import TermsView from '@/components/TermsView'
import PrivacyView from '@/components/PrivacyView'


Vue.use(Router)

export default new Router({
    mode: 'history',
    hashbang:false,
    hash : false,
    // mode:'hash',
    routes: [
      {path: '', name: 'Intro', component: Intro},
      {path: '/Login', name: 'Login', component: Login},
      {path: '/Logout', name: 'Logout', component: Logout},
      {path: '/TermsView', name: 'TermsView', component: TermsView},
      {path: '/PrivacyView', name: 'PrivacyView', component: PrivacyView},
      //   // 회원
      {path: '/Join', name:'Join', component: Join},
      {path: '/PasswordSend', name:'PasswordSend', component: PasswordSend},
      //
      // 회원가입 후 스탭 1
      {path: '/StepOne', name: 'StepOne', component: StepOne},
      // 회원가입 후 스탭 2
      {path: '/StepTwo', name: 'StepTwo', component: StepTwo},
      // 회원가입 후 스탭 3
      {path: '/StepThree', name: 'StepThree', component: StepThree},
      //
      //   // 메인
      {path: '/MainFeed', name: 'MainFeed', component: MainFeed},
      {path: '/TagFeed/:tagIdx', name: 'TagFeed', component: TagFeed},
      {path: '/MemberFeed/:memberIdx', name: 'MemberFeed', component:MemberFeed},
      {path: '/SearchFeed/', name: 'SearchFeed', component:SearchFeed},
      {path: '/Write', name: 'Write', component: Write},
      {path: '/FeedView/:feedIdx', name: 'FeedView', component: FeedView},
        {path: '/Modify', name: 'Modify', component: Modify},
      //
      //마이피드
      {path: '/MyFeed', name: 'MyFeed', component: MyFeed},
      //내설정
      {path: '/SetAccount', name: 'SetAccount', component: SetAccount},
      {path: '/SetBlock', name: 'SetBlock', component: SetBlock},
      {path: '/SetAliam', name: 'SetAliam', component: SetAliam},
      {path: '/SetBusiness', name: 'SetBusiness', component: SetBusiness},
      {path: '/SetHelp', name: 'SetHelp', component: SetHelp},
      {path: '/SetInquiry', name: 'SetInquiry', component: SetInquiry},
      {path: '/SetList', name: 'SetList', component: SetList},
      {path: '/SetNotice', name: 'SetNotice', component: SetNotice},
      {path: '/SetProfile', name: 'SetProfile', component: SetProfile},
    // { path: '*', component: NotFound }
    ],
    scrollBehavior (to, from, savedPosition) {
      return { x: 0, y: 0 }
    }
})
