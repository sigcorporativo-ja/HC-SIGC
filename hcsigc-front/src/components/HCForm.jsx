import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';

const styles = theme => ({});

class HCForm extends Component {

  createInputComponent = (children) => {
    const {
      model,
      onChange,
      onBlur,
      disabled,
      readOnly,
      inputProps,
      validator,
      onKeyPress
    } = this.props;
    return React.Children.map(children, (child) => {
      if (child.props.inputProps !== undefined) {
        return React.cloneElement(child, {
          inputProps: inputProps || child.props.inputProps,
          ...child.props,
          readOnly,
          disabled,
          value: model[child.props.name],
          onChange: typeof child.props.onChange === 'function'
            ? child.props.onChange
            : onChange,
          onBlur: typeof child.props.onBlur === 'function'
            ? child.props.onBlur
            : onBlur,
          onKeyPress: typeof child.props.onKeyPress === 'function'
            ? child.props.onKeyPress
            : onKeyPress,
          validator
        });
      } else {
        return React.cloneElement(child, {
          ...child.props,
          readOnly,
          disabled,
          value: model[child.props.name],
          onChange: typeof child.props.onChange === 'function'
            ? child.props.onChange
            : onChange,
          onBlur: typeof child.props.onBlur === 'function'
            ? child.props.onBlur
            : onBlur,
          onKeyPress: typeof child.props.onKeyPress === 'function'
            ? child.props.onKeyPress
            : onKeyPress,
          validator
        });
      }
    });
  };

  render() {
    const {children, inheritedClasses} = this.props;
    const cloneChildren = this.createInputComponent(children);
    return (<div className={inheritedClasses && inheritedClasses.root}>
      {cloneChildren}
    </div>);
  }
}

HCForm.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(HCForm));
