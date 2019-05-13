import i18next from 'i18next';
import LanguageDetector from 'i18next-browser-languagedetector';

import enUS from '../languages/en';
import esES from '../languages/es';

i18next.use(LanguageDetector).init({
  detection: {
    lookupQuerystring: 'lang'
  },
  debug: true,
  defaultNS: 'hcsigc',
  keySeparator: '.', // we use content as keys
  returnObjects: true,
  interpolation: {
    escapeValue: false, // not needed for react!!
    formatSeparator: ',',
  },
  react: {
    wait: true
  },
  resources: {
    en: {
      hcsigc: enUS
    },
    es: {
      hcsigc: esES
    }
  }
});

export default i18next;
