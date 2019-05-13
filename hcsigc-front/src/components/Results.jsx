/**
* Al envolver un componente React en la funcion withStyles de Material-UI
* las referencias se pasan mediante le parametro innerRef, no ref
*/
import React, {Component} from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';

import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableHead from '@material-ui/core/TableHead';

import Loading from 'components/Loading';
import Pagination from 'components/Pagination';

const styles = theme => ({
  root: {
    marginLeft: '9%',
    width: '80%',
    marginTop: theme.spacing.unit * 3,
    overflowX: 'auto'
  },
  star: {
    color: '#F3DA0B'
  },
  eye: {
    color: '#388e3c'
  },
  info: {
    color: '#2196f3'
  }
});

class Results extends Component {
  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    const {classes, head, data, handleChangePage, handleChangeRowsPerPage, state} = this.props;
    return (<div>
      <Paper className={classNames(classes.root)}>
        {
          this.props.searching
            ? <Loading/>
            : null
        }
        <Table className={classNames(classes.table)}>
          <TableHead>
            {head}
          </TableHead>
          <TableBody>
            {data}
            <Pagination state={state} handleChangePage={handleChangePage} handleChangeRowsPerPage={handleChangeRowsPerPage}/>
          </TableBody>
        </Table>
      </Paper>
    </div>);
  }
}

Results.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(Results));
