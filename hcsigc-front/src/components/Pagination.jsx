/**
* Al envolver un componente React en la funcion withStyles de Material-UI
* las referencias se pasan mediante le parametro innerRef, no ref
*/
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';

import PaginationActions from 'components/PaginationActions';

import TablePagination from '@material-ui/core/TablePagination';
import TableRow from '@material-ui/core/TableRow';

const styles = theme => ({
  root: {
    width: '100%',
    marginTop: theme.spacing.unit * 3
  },
  table: {
    minWidth: 1020
  },
  tableWrapper: {
    overflowX: 'auto'
  }
});

class Pagination extends Component {

  labelDisplayFunction = (t) => {
    return(obj) => {
      return `${obj.from}-${obj.to} ${t('of')} ${obj.count}`;
    };
  };

  render() {
    const {t, handleChangePage, handleChangeRowsPerPage, state} = this.props;
    return (<TableRow>
      <TablePagination rowsPerPageOptions={[5, 10, 25, 50, 100]} labelDisplayedRows={this.labelDisplayFunction(t)} labelRowsPerPage={t('rows_per_page')} count={state.count} rowsPerPage={state.rowsPerPage} page={state.page} onChangePage={handleChangePage} onChangeRowsPerPage={handleChangeRowsPerPage} ActionsComponent={PaginationActions}/>
    </TableRow>);
  }
}

Pagination.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(Pagination));
