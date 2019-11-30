import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import { 
  Button,
  Segment 
} from 'semantic-ui-react'

import Title from './Title'

import { dictionnary } from '../../Langs/langs'

export default class Dashboard extends React.Component {
  static propTypes = {
    lang: PropTypes.string,
    listSurvey: PropTypes.array
  }

  static defaultProps = {
    lang: 'fr'
  }

  render() {
    let lang = _.toUpper(this.props.lang);
    let titleSurveyStart = _.get(dictionnary, lang + '.titleSurveyStart');
    let btnCreateSurvey = _.get(dictionnary, lang + '.btnCreateSurvey');
    let listSurveyEmpty = (
      <React.Fragment>
        <Segment placeholder>
          <Title
            as='h2'
            content={_.upperFirst(titleSurveyStart)}
            icon />
          <Button
            positive
            content={_.upperFirst(btnCreateSurvey)} />
        </Segment>
      </React.Fragment>
    );

    return (
      <React.Fragment>
        {(_.size(this.props.listSurvey) === 0)?
          listSurveyEmpty
          :''
        }
      </React.Fragment>
    );
  }
}