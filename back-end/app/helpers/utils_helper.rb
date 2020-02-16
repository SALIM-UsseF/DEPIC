require 'digest'

module UtilsHelper

    # crypter sous format MD5
    def format_string_to_md5(string)
        Digest::MD5.hexdigest(string)
    end

end
