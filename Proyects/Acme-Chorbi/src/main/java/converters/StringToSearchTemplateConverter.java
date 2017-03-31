
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.MessageRepository;
import domain.Chorbi;
import domain.Message;

@Component
@Transactional
public class StringToSearchTemplateConverter implements Converter<String, SearchTemplate> {

	@Autowired
	SearchTemplateRepository	searchTemplateRepository;


	@Override
	public SearchTemplate convert(final String text) {
		SearchTemplate result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.searchTemplateRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}

}
