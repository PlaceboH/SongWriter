import { CustomValidators } from './custom-validators';

describe('CustomValidators', () => {
    it('should create an instance', () => {
        expect(new CustomValidators()).toBeTruthy();
    });
    it('when patternValidator is called it should', () => {
        // arrange
        const { build } = setup().default();
        const c = build();
        // act
    // c.patternValidator();
        // assert
        // expect(c).toEqual
    });
    it('when passwordMatchValidator is called it should', () => {
        // arrange
        const { build } = setup().default();
        const c = build();
        // act
    // c.passwordMatchValidator();
        // assert
        // expect(c).toEqual
    });
});

function setup() {
    const builder = {
        default() {
            return builder;
        },
        build() {
            return new CustomValidators();
        }
    }
    return builder;
}