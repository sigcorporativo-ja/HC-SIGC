import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import classNames from 'classnames';
import {translate} from 'react-i18next';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import TableBody from '@material-ui/core/TableBody';
import Table from '@material-ui/core/Table';

const styles = theme => ({
  root: {
    marginLeft: '9%',
    width: '80%',
    marginTop: theme.spacing.unit * 3,
    overflowX: 'auto'
  }
});

class ViewUsers extends Component {

  render() {
    const {classes, header, rows} = this.props;

    const auxHeader = (<TableRow key={Math.random()}>
      {header.map(h => <TableCell key={Math.random()}>{h}</TableCell>)}
    </TableRow>);

    const auxRows = rows.map(row => {
      return (<TableRow key={Math.random()}>
        {row.map(r => <TableCell key={Math.random()}>{r}</TableCell>)}
      </TableRow>);
    });

    return (<Table className={classNames(classes.table)}>
      <TableHead>
        {auxHeader}
      </TableHead>
      <TableBody>
        {auxRows}
      </TableBody>
    </Table>);
  }
}

ViewUsers.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(ViewUsers));
