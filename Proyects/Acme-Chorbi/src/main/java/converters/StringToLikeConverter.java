
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Folder;

@Component
@Transactional
public class StringToLikeConverter implements Converter<String, Like> {

	@Autowired
	LikeRepository	likeRepository;


	@Override
	public Like convert(final String text) {
		Folder result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.likeRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
