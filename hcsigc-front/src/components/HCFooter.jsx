import React, {Component} from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import {withStyles} from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';

const styles = theme => ({
  root: {
    backgroundColor: '#eee',
    padding: '2rem 1rem',
    display: 'flex',
    justifyContent: 'center'
  },
  textFooter: {
    fontSize: '1rem',
    fontWeight: 300
  }
});

class HCFooter extends Component {

  constructor(props) {
    super(props);
    this.state = {};
  }

  render() {
    const {classes} = this.props;
    return (<div className={classes.root}>
      <Typography className={classNames(classes.textFooter)}>
        Consejería de Economía, Hacienda y Administración Pública
      </Typography>
    </div>);
  }
}

HCFooter.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default withStyles(styles, {withTheme: true})(HCFooter);
