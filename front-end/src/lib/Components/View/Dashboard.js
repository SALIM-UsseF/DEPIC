import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import { 
  Button,
  List,
  Segment
} from 'semantic-ui-react'

import Title from './Title'
import Survey from './Survey'

import { dictionnary } from '../../Langs/langs'

export default class Dashboard extends React.Component {
  static propTypes = {
    lang: PropTypes.string,
    listSurvey: PropTypes.array,
    onCreateSurvey: PropTypes.func
  }

  static defaultProps = {
    lang: 'fr'
  }

  render() {
    let lang = _.toUpper(this.props.lang);
    let titleSurveyStart = _.get(dictionnary, lang + '.titleSurveyStart');
    let createSurvey = _.get(dictionnary, lang + '.createSurvey');
    let emptySurvey = (
      <React.Fragment>
        <Segment placeholder>
          <Title
            as='h2'
            content={_.upperFirst(titleSurveyStart)}
            icon />
          <Button
            positive
            content={_.upperFirst(createSurvey)}
            onClick={() => {
              if (this.props.onCreateSurvey) {
                this.props.onCreateSurvey('createSurvey');
              }
            }} />
        </Segment>
      </React.Fragment>
    );
    let survey = (
      <React.Fragment>
        <List
          divided 
          verticalAlign='middle'
        >
          {_.map(this.props.listSurvey, survey => (
            <List.Item>
              <Survey />
            </List.Item>
          ))}
        </List>
      </React.Fragment>
    );

    return (
      <React.Fragment>
        {(_.size(this.props.listSurvey) === 0)?
          emptySurvey
          :survey
        }
      </React.Fragment>
    );
  }
}