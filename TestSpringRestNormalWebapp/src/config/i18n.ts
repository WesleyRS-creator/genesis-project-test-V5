/* Import lang Files */
import english from '@/lang/english'

import { createI18n } from 'vue-i18n'
const i18n = createI18n({
  legacy: false,
  locale: 'en',
  fallbackLocale: 'en',
  messages: {
    en: english.messages,
  },
  datetimeFormats: {
    en: english.dateTimeFormats,
  },
  numberFormats: {
    en: english.numberFormats,
  },
})
export default i18n
