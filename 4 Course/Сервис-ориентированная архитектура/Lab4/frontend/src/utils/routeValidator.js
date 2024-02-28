import get from 'lodash.get';
import set from 'lodash.set';

export const validate = (route) => {
  const feedback = {};

  if (Number(get(route, 'distance', '')) <= 1)
    set(feedback, 'distance', 'Distance must be greater than 1');
  if (Number(get(route, 'coordinates.y', '')) > 488)
    set(feedback, 'coordinates.y', 'Сoordinates.y must be no greater than 488');

  const notNullFields = [
    'name',
    'from.coordinates.x',
    'from.coordinates.y',
    'from.name',
    'to.coordinates.x',
    'to.coordinates.y',
    'to.name',
    'distance'
  ];

  notNullFields.forEach((field) => {
    if (get(route, field, '') === '')
      set(feedback, field, field.firstLetterToUppercase() + ' must not be empty');
  });

  return feedback;
};
