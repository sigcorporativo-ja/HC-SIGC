import React, {Component} from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';
import {withStyles} from '@material-ui/core/styles';

import LinearProgress from '@material-ui/core/LinearProgress';

const styles = theme => ({
  progress: {
    margin: 0
  },
  load: {
    margin: 0
  }
});

class Loading extends Component {
  render() {
    const {classes} = this.props;
    return (<div style={this.props.display} className={classes.load}>
      <LinearProgress style={this.props.display} className={classNames(classes.progress)} color="primary" size={70}/>
    </div>);
  }
}

Loading.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default withStyles(styles, {withTheme: true})(Loading);
