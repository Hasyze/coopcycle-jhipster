import { TranslatorContext, Storage } from 'react-jhipster';

import { setLocale } from 'app/shared/reducers/locale';

TranslatorContext.setDefaultLocale('fr');
TranslatorContext.setRenderInnerTextForMissingKeys(false);

export const languages: any = {
  'ar-ly': { name: 'العربية', rtl: true },
  by: { name: 'Беларускі' },
  'zh-cn': { name: '中文（简体）' },
  en: { name: 'English' },
  et: { name: 'Eesti' },
  fa: { name: 'فارسی', rtl: true },
  fr: { name: 'Français' },
  ua: { name: 'Українська' },
  'uz-Cyrl-uz': { name: 'Ўзбекча' },
  vi: { name: 'Tiếng Việt' },
  // jhipster-needle-i18n-language-key-pipe - JHipster will add/remove languages in this object
};

export const locales = Object.keys(languages).sort();

export const isRTL = (lang: string): boolean => languages[lang] && languages[lang].rtl;

export const registerLocale = store => {
  store.dispatch(setLocale(Storage.session.get('locale', 'fr')));
};
