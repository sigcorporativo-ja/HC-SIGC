import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';
import classNames from 'classnames';
import FormHelperText from '@material-ui/core/FormHelperText';
import InputLabel from '@material-ui/core/InputLabel';

import Select from 'react-select';

const styles = theme => ({
  select: {
    paddingTop: '0.5rem',
    width: '100%'
  },
  root: {
    width: '100%'
  }
});

class SelectSearchOptions extends Component {

  constructor(props) {
    super(props);
    const selectedOption = this.createSelectedOption(this.props.value);
    this.state = {
      selectedOption: selectedOption || {}
    };
  }

  createSelectedOption = (value) => {
    return Array.isArray(value)
      ? value.map(obj => {
        return {
          value: obj.id || obj.value,
          label: obj.name || obj.label
        }
      })
      : {
        value: value.id || value.value,
        label: value.name || value.label
      };
  }

  onChange = (selectedOption) => {
    const {onChange, name} = this.props;
    if (typeof onChange === 'function') {
      this.menuIsOpen = false;
      const e = {
        target: {
          name,
          value: selectedOption || {}
        }
      };
      onChange(e);
    }
  };

  onInputChange = (value) => {
    if (value.length > 3) {
      this.menuIsOpen = true;
      this.forceUpdate();
    }
  }

  closeMenu = () => {
    const {searchOpenMenu} = this.props;
    this.menuIsOpen = searchOpenMenu;
    this.forceUpdate();
  }

  close = () => {
    this.menuIsOpen = false;
    this.forceUpdate();
  }

  open = () => {
    const {searchOpenMenu} = this.props;
    if (searchOpenMenu) {
      this.menuIsOpen = true;
    }
    this.forceUpdate();
  }

  render() {
    const {
      options,
      textLabel,
      classes,
      key,
      value,
      validator,
      name,
      isMulti,
      isDisabled,
      styles,
      title
    } = this.props;
    const {menuIsOpen} = this;

    let helper = null;
    const error = validator && validator[name]
      ? validator[name].isInvalid
      : false;
    const message = validator && validator[name]
      ? validator[name].message
      : '';
    if (error === true) {
      helper = (<FormHelperText error={true}>{message}</FormHelperText>);
    }

    const optionsSelect = options.map((obj) => {
      return {value: obj.id, label: obj.name}
    });

    const selectedOption = this.createSelectedOption(value);

    return (<div name={name} className={classes.root}>
      {
        title && <InputLabel>
            {textLabel}
          </InputLabel>
      }
      <Select key={key} fullWidth={true} backspaceRemovesValue={false} styles={styles} className={classNames(classes.select)} isMulti={isMulti} onMenuOpen={this.open} onMenuClose={this.close} onFocus={this.closeMenu} menuIsOpen={menuIsOpen} isDisabled={isDisabled} inputProps={{
          error
        }} value={selectedOption} placeholder={textLabel} onInputChange={this.onInputChange} onChange={this.onChange} options={optionsSelect}/> {helper}
    </div>);
  }
}
SelectSearchOptions.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};
export default translate()(withStyles(styles, {withTheme: true})(SelectSearchOptions));
