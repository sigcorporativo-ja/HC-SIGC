/**
 * Resolve strings url
 * @function
 */
export const pathResolve = (path, context) => {
  const newPath = path.replace(/(\/+)$/, '');
  const newContext = context.replace(/\/+/, '');
  return [newPath, newContext].join('/');
};

/**
 * This function take an obj with the next estructure:
 * {
 ..,
 input:{
   propName: {
      value,
      ...
      }
   }
 }
 * y returns {propName: value}
 * @function
 */
export const jsonToModel = (obj) => {
  const json = {};
  Object.keys(obj).forEach(key => {
    if (obj[key].value.toString().trim() !== '' || Array.isArray(obj[key].value)) {
      json[key] = obj[key].value;
    }
  });
  return json;
};

/**
 * y returns {propName: value}
 * @function
 */
export const modelToJson = (obj) => {
  const model = {};
  Object.keys(obj).forEach(key => {
    model[key] = {
      value: obj[key] || ''
    };
  });
  return model;
};
