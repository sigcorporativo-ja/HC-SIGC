import React, {Component} from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {translate} from 'react-i18next';
import Autosuggest from 'react-autosuggest';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import TextField from '@material-ui/core/TextField';
import Paper from '@material-ui/core/Paper';
import FormHelperText from '@material-ui/core/FormHelperText';

import match from 'autosuggest-highlight/match';
import parse from 'autosuggest-highlight/parse';

const renderInputComponent = (inputProps) => {
  const {
    classes,
    inputRef = () => {},
    ref,
    ...other
  } = inputProps;

  return (<TextField fullWidth={true} InputProps={{
      inputRef: node => {
        ref(node);
        inputRef(node);
      },
      classes: {
        input: classes.input
      }
    }} {...other}/>);
};

const renderSuggestion = (suggestion, {query, isHighlighted}) => {
  const matches = match(suggestion.label, query);
  const parts = parse(suggestion.label, matches);

  return (<MenuItem selected={isHighlighted} component="div">
    <div>
      {
        parts.map((part, index) => {
          return part.highlight
            ? (<span key={String(index)} style={{
                fontWeight: 500
              }}>
              {part.text}
            </span>)
            : (<strong key={String(index)} style={{
                fontWeight: 300
              }}>
              {part.text}
            </strong>);
        })
      }
    </div>
  </MenuItem>);
}

const styles = theme => ({
  root: {
    flexGrow: 1
  },
  container: {
    position: 'relative'
  },
  suggestionsContainerOpen: {
    position: 'absolute',
    zIndex: 1,
    marginTop: theme.spacing.unit,
    left: 0,
    right: 0
  },
  suggestion: {
    display: 'block'
  },
  suggestionsList: {
    margin: 0,
    padding: 0,
    listStyleType: 'none'
  },
  divider: {
    height: theme.spacing.unit * 2
  }
});

class SelectSearch extends Component {

  onChange = (event) => {
    const {onChange} = this.props;
    if (typeof onChange === 'function') {
      onChange(event);
    }
  };

  getSuggestionValue = (suggestion) => {
    const objId = {
      'id': suggestion.value
    };
    this.setState({id: objId});
    return suggestion.label;
  };

  getSuggestions = (value) => {
    this.setState({id: 0});
    const inputValue = value.trim().toLowerCase();
    const inputLength = inputValue.length;

    return inputLength === 0
      ? []
      : this.suggestions.filter(lang => lang.label.toLowerCase().includes(inputValue));
  };

  onSuggestionsFetchRequested = ({value}) => {
    this.setState({
      ...this.state,
      value,
      suggestions: this.getSuggestions(value)
    });
  };

  onSuggestionsClearRequested = () => {
    const {options} = this.props;
    const optionsSelect = options.map((obj) => {
      return {value: obj.id, label: obj.name}
    });
    this.setState({suggestions: optionsSelect});
  };

  componentDidMount() {
    const {options} = this.props;
    const suggestions = Array.isArray(options)
      ? options.map((obj) => {
        return {value: obj.id, label: obj.name}
      })
      : [];
    this.suggestions = suggestions;
  }

  render() {
    const {
      value,
      classes,
      textLabel,
      textHolder,
      validator,
      name,
      options
    } = this.props;
    const inputProps = {
      placeholder: textHolder,
      value,
      name,
      classes,
      onChange: this.onChange
    };

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

    const suggestions = Array.isArray(options)
      ? options.map((obj) => {
        return {value: obj.id, label: obj.name}
      })
      : [];

    return (<div className={classes.root}>
      <InputLabel htmlFor="select">{textLabel}</InputLabel>
      <Autosuggest renderInputComponent={renderInputComponent} theme={{
          container: classes.container,
          suggestionsContainerOpen: classes.suggestionsContainerOpen,
          suggestionsList: classes.suggestionsList,
          suggestion: classes.suggestion
        }} renderSuggestionsContainer={options => (<Paper {...options.containerProps} square={true}>
          {options.children}
        </Paper>)} suggestions={suggestions} inputProps={inputProps} getSuggestionValue={getSuggestionValue} onSuggestionsFetchRequested={onSuggestionsFetchRequested} onSuggestionsClearRequested={onSuggestionsClearRequested} renderSuggestion={renderSuggestion}/>{helper}
    </div>);
  }

}

SelectSearch.propTypes = {
  classes: PropTypes.object.isRequired,
  theme: PropTypes.object.isRequired
};

export default translate()(withStyles(styles, {withTheme: true})(SelectSearch));
