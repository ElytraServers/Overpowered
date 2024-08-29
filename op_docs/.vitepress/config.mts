import {defineConfig} from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
  title: "Overpowered Wiki",
  description: "Overpowered Documentation and Description",

  locales: {
    root: {
      label: "简体中文",
      lang: "zh"
    },
    // en: {
    //   label: "English",
    //   lang: "en"
    // }
  },

  markdown: {
    math: true
  },

  themeConfig: {
    // https://vitepress.dev/reference/default-theme-config
    nav: [
      { text: 'Home', link: '/' },
      // { text: 'Examples', link: '/markdown-examples' }
    ],

    sidebar: [
      {
        text: "OP",
        items: [
          { "text": "关于", link: "/about" }
        ]
      },
      {
        text: "机器",
        items: [
          { "text": "有源变压器机械架", link: "/active_transformer_rack" },
          { "text": "二合一输入仓", link: "/unihatch" },
          { "text": "平衡输出仓", link: "/balanced_output_hatch" },
        ]
      },
      {
        text: "物品",
        items: [
          { "text": "编程卡", link: "/programmed_upgrade" },
        ]
      },
      {
        text: "机制",
        items: [
          // { text: 'Markdown Examples', link: '/markdown-examples' },
          // { text: 'Runtime API Examples', link: '/api-examples' },
          { text: "保险", link: "/insurance" },
          { text: "电压概率加成", link: "/voltage_tier_chance_bonus" },
          { text: "其他", link: "/misc" },
        ]
      }
    ],

    socialLinks: [
      { icon: 'github', link: 'https://github.com/ElytraServers/Overpowered' }
    ],

    footer: {
      message: "Released under the MIT License.",
      copyright: "Copyright &copy; 2024-now <a href='https://github.com/Taskeren'>Taskeren</a> and all Contributors",
    },

    editLink: {
      pattern: "https://github.com/ElytraServers/Overpowered/edit/master/op_docs/:path",
    }
  }
})
