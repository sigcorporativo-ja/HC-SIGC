/**
* Al envolver un componente React en la funcion withStyles de Material-UI
* las referencias se pasan mediante le parametro innerRef, no ref
*/
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';

import IconButton from '@material-ui/core/IconButton';
import KeyboardArrowLeft from '@material-ui/icons/KeyboardArrowLeft';
import KeyboardArrowRight from '@material-ui/icons/KeyboardArrowRight';

const styles = theme => ({
  root: {
    flexShrink: 0,
    color: theme.palette.text.secondary,
    marginLeft: theme.spacing.unit * 2.5
  }
});

class PaginationActions extends Component {

  handleBackButtonClick = event => {
    this.props.onChangePage(event, this.props.page - 1);
  };

  handleNextButtonClick = event => {
    this.props.onChangePage(event, this.props.page + 1);
  };

  render() {
    const {classes, count, page, rowsPerPage, theme} = this.props;

    return (<div className={classes.root}>
      <IconButton onClick={this.handleBackButtonClick} disabled={page === 0} aria-label="Previous Page">
        {
          theme.direction === 'rtl'
            ? <KeyboardArrowRight value={'right'}/>
            : <KeyboardArrowLeft value={'left'}/>
        }
      </IconButton>
      <IconButton onClick={this.handleNextButtonClick} disabled={page >= Math.ceil(count / rowsPerPage) - 1} aria-label="Next Page">
        {
          theme.direction === 'rtl'
            ? <KeyboardArrowLeft value={'left'}/>
            : <KeyboardArrowRight value={'right'}/>
        }
      </IconButton>
    </div>);
  }
}

PaginationActions.propTypes = {
  classes: PropTypes.object.isRequired,
  count: PropTypes.number.isRequired,
  onChangePage: PropTypes.func.isRequired,
  page: PropTypes.number.isRequired,
  rowsPerPage: PropTypes.number.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(PaginationActions));
