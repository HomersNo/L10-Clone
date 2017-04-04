
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.LikeRepository;
import domain.Likes;

@Component
@Transactional
public class StringToLikeConverter implements Converter<String, Likes> {

	@Autowired
	LikeRepository	likeRepository;


	@Override
	public Likes convert(final String text) {
		Likes result;
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
