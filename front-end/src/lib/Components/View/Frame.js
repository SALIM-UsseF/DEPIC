import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'


import Title from '../Title'
import Dashboard from './Dashboard'
import CreateSurvey from './CreateSurvey'
import Settings from './Settings'

import { dictionnary } from '../../Langs/langs'

export default class Frame extends React.Component {
  static propTypes = {
    lang: PropTypes.string,
    title: PropTypes.string,
    onCreateSurvey: PropTypes.func
  }

  static defaultProps = {
    lang: 'fr'
  }

  state = {
    title: this.props.title
  }

  static getDerivedStateFromProps(props, state) {
    return {title: props.title};
  }

  onCreateSurvey = title => {
    if (this.props.onCreateSurvey) {
      this.props.onCreateSurvey(title);
    }
  }

  render() {
    let lang = _.toUpper(this.props.lang);
    let title = _.get(dictionnary, lang + '.' + this.state.title);

    let dashboard = (
      <React.Fragment>
        <Dashboard
          lang={this.props.lang}
          listSurvey={[]}
          onCreateSurvey={this.onCreateSurvey} />
      </React.Fragment>
    );
    let createSurvey = (
      <React.Fragment>
        <CreateSurvey
          lang={this.props.lang} />
      </React.Fragment>
    );
    let settings = (
      <React.Fragment>
        <Settings
          lang={this.props.lang} />
      </React.Fragment>
    );

    return (
      <React.Fragment>
        <Title
          as='h1'
          content={_.upperFirst(title)}
          color='teal' />
        
        {(this.state.title === 'dashboard')?
          dashboard
          :(this.state.title === 'createSurvey')?
            createSurvey
            :(this.state.title === 'settings')?
              settings
              :''
        }
      </React.Fragment>
    );
  }
}