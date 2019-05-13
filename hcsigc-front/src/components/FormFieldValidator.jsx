import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';

const styles = theme => ({
  btn: {
    display: 'flex',
    flexDirection: 'row'
  },
  icon: {
    marginLeft: '5px'
  },
  title: {
    backgroundColor: '#1C74A6'
  },
  dialogContent: {
    display: 'flex',
    flexDirection: 'column',
    AlignContent: 'space-between',
    justifyContent: 'flex-start',
    padding: '6%'
  }
});

class FormFieldValidator extends Component {

  static defaultProps = {
    notNull: '',
    notEmpty: '',
    required: '',
    valuePropField: 'value',
    customValidations: []
  }

  constructor(props) {
    super(props);
    const {
      children,
      valuePropField,
      notNull,
      notEmpty,
      required,
      customValidations
    } = this.props;

    this.originalOnChangeEvt = children.props.onChange;
    this.validations = {
      notNull: (value) => value != null,
      notEmpty: (value) => value.trim() !== ''
    };

    let validations = customValidations;
    if (required !== '') {
      validations.push({validation: this.validations.notNull, message: required});
      validations.push({validation: this.validations.notEmpty, message: required});
    } else {
      if (notNull !== '') {
        validations.push({validation: this.validations.notNull, message: notNull});
      }
      if (notEmpty !== '') {
        validations.push({validation: this.validations.notEmpty, message: notEmpty});
      }
    }

    this.state = {
      dirty: false,
      validations: validations,
      messages: []
    }
  }

  componentWillReceiveProps(nextProps) {
    const {valuePropField, onValidStateChange} = this.props;
    const getVal = (props, field) => props.children.props[field];

    let currValid = this.isValid(getVal(this.props, valuePropField));
    let nextValid = this.isValid(getVal(nextProps, valuePropField));

    if (this.state.forceValidStateChange || (currValid !== nextValid && typeof onValidStateChange === 'function')) {
      if (this.state.forceValidStateChange) {
        this.setState({
          ...this.state,
          forceValidStateChange: false
        }, () => onValidStateChange(nextValid, nextProps.children.props.id || 'unkown'))
      } else {
        onValidStateChange(nextValid, nextProps.children.props.id || 'unkown');
      }
    }
  }

  onInputChange(evt) {
    const {onValidStateChange, children} = this.props;
    const {dirty} = this.state;

    let value = evt.target.value;
    this.setState({
      ...this.state,
      forceValidStateChange: !dirty,
      dirty: true
    }, () => {
      this.originalOnChangeEvt({
        target: {
          value: value
        }
      })
    });
  }

  isValid(value) {
    return this.state.validations.map(v => !v.validation(value)).reduce((tot, curr) => !tot || !curr, true);
  }

  getMessages(value) {
    return this.state.validations.filter(v => !v.validation(value)).map(v => v.message).filter((item, pos, self) => self.indexOf(item) == pos)
  }

  render() {
    const {children, valuePropField} = this.props;
    const {dirty} = this.state;

    let value = children.props[valuePropField];

    let valid = this.isValid(value);
    let messages = this.getMessages(value);

    return React.cloneElement(children, {
      ...children.props,
      onChange: this.onInputChange.bind(this),
      error: dirty && !valid,
      errorMessages: messages
    });
  }

}

FormFieldValidator.propTypes = {
  notNull: PropTypes.string,
  notEmpty: PropTypes.string,
  required: PropTypes.string,
  valuePropField: PropTypes.string,
  onValidStateChange: PropTypes.func,
  customValidations: PropTypes.arrayOf(PropTypes.shape({validation: PropTypes.func, message: PropTypes.string}))
}

export default translate()(withStyles(styles, {withTheme: true})(FormFieldValidator));
