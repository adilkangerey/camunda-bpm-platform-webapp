module.exports = function(config, localesConfig, pathConfig) {
  'use strict';
  console.log(pathConfig.appName + ' locales here ' + pathConfig.buildTarget);
  localesConfig[pathConfig.appName + '_locales'] = {
      options: {
        dest: pathConfig.buildTarget + '/locales',
        onlyProd: 1,
        anOption: 'for production'
      },
      src: [
        '<%= pkg.gruntConfig.enTranslationDir %>/' + pathConfig.appName + '/en.json'
      ]
  };
};
