/**
* Al envolver un componente React en la funcion withStyles de Material-UI
* las referencias se pasan mediante le parametro innerRef, no ref
*/
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {translate} from 'react-i18next';
import classNames from 'classnames';
import {withStyles} from '@material-ui/core/styles';

import FormControl from '@material-ui/core/FormControl'
import Input from '@material-ui/core/Input'
import InputLabel from '@material-ui/core/InputLabel'


const styles = theme => ({
  root: {
    marginLeft: '150px',
    margin: theme.spacing.unit,
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignSelf: 'center'
  }
});

class SearchInput extends Component {

  onInput = (e) => {
    const {onInput} = this.props;
    if (typeof onInput === 'function') {
      onInput(e);
    }
  };

  render() {
    const {classes, t} = this.props;
    return (<FormControl className={classNames(classes.root)}>
      <InputLabel htmlFor="component-simple">{t('search')}</InputLabel>
      <Input onKeyUp={this.onInput}/>
    </FormControl>);
  }
}

SearchInput.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(SearchInput));
