const validator = {
  passwordMatch: (confirmation, state) => (state.password === confirmation),
  isEmail: (state) => {
    const regexp = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;
    return regexp.test(state);
  },
  dateGTE: (date, state) => {
    const from = Date.parse(date);
    const to = Date.parse(state.endDate);

    let bool = false;
    if (to < from) {
      bool = true;
    }
    return bool;
  },
  dateLTE: (date, state) => {
    const from = Date.parse(state.startDate);
    const to = Date.parse(date);

    let bool = false;
    if (to < from) {
      bool = true;
    }
    return bool;
  },
  isEmpty: (state) => {
    const emptyString = typeof state === 'string' && state.trim() === '';
    const emptyObject = typeof state === 'object'
      ? Object.keys(state).length === 0
      : false;
    return emptyString || emptyObject;
  },
  noStartNumber: (value) => {
    return !isNaN(parseFloat(value.substr(0, 1)));
  },
  noSpecialCharacter: (value) => {
    const regexp = /`|ª|º|~|!|@|#|\$|%|\^|&|\*|\(|\)|\+|=|\[|\{|\]|\}|\||\\|'|<|,|\.|>|\?|\/|""|;|:/;
    return regexp.test(value);
  },
  isInt: (confirmation, array, state) => {
    let min;
    let max;
    let inRange = true;
    if (Array.isArray(array)) {
      [min, max] = [
        array[0], array[1]
      ];
      inRange = state >= min && state <= max;
    }
    return Number.isInteger(state) && inRange;
  },
  lengthInRange: (confirmation, array, state) => state.length >= array[0] && state.length <= array[1]
};

class FormValidator {
  constructor(validations) {
    this.validations = validations;
  }

  validate(state) {
    let validation = this.valid();

    this.validations.forEach(rule => {

      if (!validation[rule.field].isInvalid) {
        const field_value = state[rule.field];
        const args = rule.args || [];
        const validation_method = typeof rule.method === 'string'
          ? validator[rule.method]
          : rule.method
        if (validation_method(field_value, ...args, state) !== rule.validWhen && rule.hasChanged) {
          validation[rule.field] = {
            isInvalid: true,
            message: rule.message
          }
          validation.isValid = false;
        }
      }
    });

    return validation;
  }

  valid() {
    const validation = {}

    this.validations.map(rule => (validation[rule.field] = {
      isInvalid: false,
      message: ''
    }));

    return {
      isValid: true,
      ...validation
    };
  }

  hasChanged(name) {
    this.validations.forEach(rule => {
      if (name === rule.field) {
        rule.hasChanged = true;
      }
      return rule;
    });
  }

  hasChangedAll(flag) {
    this.validations.forEach(rule => rule.hasChanged = flag);
  }
}

export default FormValidator;
