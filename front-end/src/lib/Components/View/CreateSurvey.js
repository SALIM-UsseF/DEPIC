import React from 'react'
import PropTypes from 'prop-types'
import _ from 'lodash'

import { MainQuestion } from './../Question'

// import { dictionnary } from '../../Langs/langs'

import Title from '../Title'
import { TextArea, Form, Divider, Button } from 'semantic-ui-react'

export default class CreateSurvey extends React.Component {
  static propTypes = {
    client: PropTypes.any.isRequired,
    lang: PropTypes.string,
    idSondage: PropTypes.number,
    modifying: PropTypes.bool
  }

  static defaultProps = {
    lang: 'fr'
  }

  state = {
    idSondage: 0,
    nomSondage: '',
    questions: [],
    descriptionSondage: '',
  }

  componentDidMount() {
    if (this.props.modifying) {
      this.props.client.Sondage.sondage(
        this.props.idSondage,
        result => {
          this.props.client.Question.readBySondage(
            result.data.id_sondage,
            success => {
              console.log(success.data)
              this.setState({
                idSondage: result.data.id_sondage,
                nomSondage: result.data.intituleSondage,
                descriptionSondage: result.data.descriptionSondage,
                questions: success.data
              })
            },
            error => {
              console.log(error)
            }
          )
        },
        error => {
          console.log(error)
        }
      )
    }
  }

  render() {
    let key = 0;
    let creation = (
      <React.Fragment>

      </React.Fragment>
    );
    let modification = (
      <React.Fragment>
        <Title
          as='h1'
          content={this.state.nomSondage}
          color='teal' />
      
        <Form>
          <TextArea
            value={this.state.descriptionSondage}
            onChange={(e, value) => {
              this.setState({
                descriptionSondage: value.value
              })
            }}
            style={{ minHeight: 100 }}
          />
        </Form>
        <Divider hidden />
        {_.map(this.state.questions, question => {
          key++;

          return (               
            <React.Fragment key={key}>
              <MainQuestion
                client={this.props.client}
                isModifying={this.props.modifying}
                idQuestion={question.id_question}
                numberOfQuestion={key}
                type={question.type}
                intituleQuestion={question.intitule}
                isOptional={question.estObligatoire}
                nbChoix={question.nombreChoix}
                isUnique={question.estUnique}
                nbCharactere={question.nombreDeCaractere}
                maxPoints={question.maxPoints} />
            </React.Fragment>
          )
        })}
        <div style={{ marginBottom:"50px" }}>
          <Button
            content='Enregistrer'
            floated='right'
            positive
          />
          <Button
            content='Annuler'
            floated='right'
            basic
            color='black'
          />
        </div>
      </React.Fragment>
    );

    return (
      <React.Fragment>
        {this.props.modifying?
          modification
          :creation
        }
      </React.Fragment>
    );
  }
}