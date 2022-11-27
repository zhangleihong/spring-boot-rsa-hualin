import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [

	{
		path: '/',
		name: 'Front',
		component: () => import('../views/Front'),
		children: [{
			path: 'entryApplication',
			name: 'entryApplication',
			component: () => import('../views/entryApplication.vue')
		},
		{
			path: 'searchPage',
			name: 'searchPage',
			component: () => import('../views/searchPage.vue')
		},
		{
			path: 'entryChange',
			name: 'entryChange',
			component: () => import('../views/entryChange.vue')
		},
	]
	},

]

const router = new VueRouter({
	mode: 'history',
	base: process.env.BASE_URL,
	routes
})

export default router
